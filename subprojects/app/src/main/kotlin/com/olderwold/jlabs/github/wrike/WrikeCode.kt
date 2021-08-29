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
        val adapter = Adapter(this)
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

    fun navigateToFullScreen(data: MainViewModel.Data) {
        // some navigate code
    }
}

class MainViewModel(
    private val executorService: ExecutorService,
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
        val newData = remoteRequestForData()
            .map { Data(it) }
            .let(Collections::unmodifiableList)
        _data.set(newData)

        // We need to make sure we post to UI thread
        _dataLive.postValue(newData)
    }

    override fun onCleared() {
        executorService.shutdown()
    }

    private fun remoteRequestForData(): List<String> {
        return listOf("Mesocricetus auratus", "Phodopus sungorus", "Phodopus campbelli")
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
                executorService = Executors.newSingleThreadExecutor()
            ) as T
        }
    }
}

// Adapter for recycler view
class Adapter(
    private val fragment: MainFragment
) : ListAdapter<MainViewModel.Data, Adapter.DataHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(ItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        with(getItem(position)) {
            holder.itemBinding.title.text = title
            holder.itemView.setOnClickListener { fragment.navigateToFullScreen(this) }
        }
    }

    class DataHolder(val itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

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
