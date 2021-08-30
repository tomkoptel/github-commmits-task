package com.olderwold.jlabs.github.wrike

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference

// Fragment display recycler view with a list of items
// User has ability to filter items
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<MainViewModel>(
        factoryProducer = { MainViewModel.Factory }
    )
    private var textWatcher: TextWatcher? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = Adapter {
            navigateToFullScreen(it)
        }
        binding.recycler.adapter = adapter
        viewModel.dataLive.observe(viewLifecycleOwner) { data ->
            adapter.submitList(requireNotNull(data) {
                "Make sure that we don't instantiate VM with nullable state"
            })
        }
        textWatcher = binding.filterEdit.addTextChangedListener {
            viewModel.filter(it.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textWatcher?.let {
            binding.filterEdit.removeTextChangedListener(it)
        }
        textWatcher = null
    }

    private fun navigateToFullScreen(data: MainViewModel.Data) {
        // some navigate code
    }
}

class MainViewModel(
    private val executorService: ExecutorService,
    private val getData: GetData,
) : ViewModel() {
    private val _data = AtomicReference<List<Data>>(emptyList())
    private val dataList: List<Data> get() = _data.get()
    private val _dataLive = MutableLiveData(dataList)

    val dataLive: LiveData<List<Data>> = _dataLive

    init {
        executorService.execute(::loadData)
    }

    @WorkerThread
    private fun loadData() {
        val newData = getData()
            .map { Data(it) }
            .let(Collections::unmodifiableList)
        _data.set(newData)

        // We need to make sure we post to UI thread
        _dataLive.postValue(newData)
    }

    override fun onCleared() {
        executorService.shutdown()
    }

    @UiThread
    fun filter(query: String) {
        val filteredData = dataList.filter { data -> data.title.contains(query, ignoreCase = true) }
        _dataLive.value = filteredData
    }

    // Single item data class
    data class Data(var title: String)

    object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(
                executorService = Executors.newSingleThreadExecutor(),
                getData = GetDataStatic()
            ) as T
        }
    }
}

class GetDataStatic : GetData {
    override operator fun invoke(): List<String> {
        return listOf("Mesocricetus auratus", "Phodopus sungorus", "Phodopus campbelli")
    }
}

fun interface GetData {
    operator fun invoke(): List<String>
}

// Adapter for recycler view
class Adapter(
    private val navigateToFullScreen: (MainViewModel.Data) -> Unit,
) : ListAdapter<MainViewModel.Data, Adapter.DataHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemBinding.inflate(inflater)
        return DataHolder(itemBinding, navigateToFullScreen)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    class DataHolder(
        private val itemBinding: ItemBinding,
        private val navigateToFullScreen: (MainViewModel.Data) -> Unit,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: MainViewModel.Data) {
            itemBinding.title.text = item.title
            itemView.setOnClickListener { navigateToFullScreen(item) }
        }
    }

    object ItemCallback : DiffUtil.ItemCallback<MainViewModel.Data>() {
        override fun areItemsTheSame(
            oldItem: MainViewModel.Data,
            newItem: MainViewModel.Data
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: MainViewModel.Data,
            newItem: MainViewModel.Data
        ): Boolean {
            return oldItem == newItem
        }
    }
}

class FragmentMainBinding(private val context: Context) {
    val root: View get() = View(context)
    val recycler: RecyclerView get() = RecyclerView(context)
    val filterEdit: EditText get() = EditText(context)

    companion object {
        fun inflate(inflater: LayoutInflater): FragmentMainBinding {
            return FragmentMainBinding(inflater.context)
        }
    }
}

class ItemBinding(private val context: Context) {
    val root: View get() = View(context)
    val title: TextView get() = TextView(context)

    companion object {
        fun inflate(inflater: LayoutInflater): ItemBinding {
            return ItemBinding(inflater.context)
        }
    }
}
