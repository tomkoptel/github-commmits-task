package com.olderwold.jlabs.github.wrike

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

// Fragment display recycler view with a list of items
// User has ability to filter items
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<MainViewModel>()
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
        viewModel.dataLive.observe(viewLifecycleOwner) {
            adapter.items = it
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

class MainViewModel : ViewModel() {
    val dataLive = MutableLiveData<MutableList<Data>>()

    private val thread: Thread
        get() = Thread {
            dataLive.value?.clear()
            dataLive.value = dataLive.value?.apply {
                addAll(remoteRequestForData().map { Data(it) })
            }
        }

    init {
        thread.start()
    }

    override fun onCleared() {
        try {
            thread.join()
        } catch (ex: InterruptedException) {
            Log.d("MainViewModel", "onCleared() thread was interrupted", ex)
        }
    }

    private fun remoteRequestForData(): MutableList<String> {
        return mutableListOf("Mesocricetus auratus", "Phodopus sungorus", "Phodopus campbelli")
    }

    fun filter(query: String) {
        dataLive.value?.filter { it.title.contains(query) }
    }

    // Single item data class
    data class Data(var title: String)
}

// Adapter for recycler view
class Adapter(private val fragment: MainFragment) : RecyclerView.Adapter<Adapter.DataHolder>() {

    var items: List<MainViewModel.Data> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(ItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.itemBinding.title.text = items[position].title
        holder.itemView.setOnClickListener { fragment.navigateToFullScreen(items[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class DataHolder(val itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
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
