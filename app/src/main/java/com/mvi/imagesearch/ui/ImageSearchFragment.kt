package com.mvi.imagesearch.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvi.imagesearch.Adapter
import com.mvi.imagesearch.R
import com.mvi.imagesearch.Repository
import com.mvi.imagesearch.cache.getDatabase
import com.mvi.imagesearch.constants.Constant.Companion.ROW_1
import com.mvi.imagesearch.constants.Constant.Companion.ROW_2
import com.mvi.imagesearch.constants.Constant.Companion.ROW_3
import com.mvi.imagesearch.constants.Constant.Companion.ROW_4
import com.mvi.imagesearch.model.ApiModel
import com.mvi.imagesearch.model.ViewState
import com.mvi.imagesearch.utils.NetworkHelper
import kotlinx.android.synthetic.main.fragment_image_search.*


class ImageSearchFragment : Fragment() {

    lateinit var adapterImage: Adapter
    lateinit var manager: GridLayoutManager
    private val list = mutableListOf<ApiModel>()

    private lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<ImageViewModel>(
        factoryProducer = { viewModelFactory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_image_search, container, false)

        viewModelFactory = ViewModelFactory(Repository(getDatabase(requireContext()), NetworkHelper(requireContext())))
        manager = GridLayoutManager(activity, 2)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initRecycler()


        button.setOnClickListener {
            val query = editText.text.toString()
           // hideKeyboard()

            if(query.isBlank()) {
                Toast.makeText(context, "No text to search", Toast.LENGTH_SHORT)
                    .show()
            }else {
                list.clear()
                adapterImage.submitList(list)
                viewModel.query(query)
            }

        }





        viewModel.viewState.observe(viewLifecycleOwner, Observer {  state ->

            when(state){
                is ViewState.Success -> {
                    for ( i in state.List){
                        list.add(i)
                    }
                    adapterImage.submitList(state.List)
                    adapterImage.notifyDataSetChanged()
                   // recyclerView.smoothScrollToPosition(list.size - state.List.size)
                    Log.d("UI","\n\n  ${list.size}  ad  : ")

                }
                is ViewState.Failed -> {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }
                is ViewState.Loading -> {
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }
            }

        })




    }

    private fun initRecycler() {
        recyclerView.apply {
            adapterImage  = Adapter(/*this@ImageSearchFragment*/)
            layoutManager = manager

            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == adapter?.itemCount?.minus(1)) {
                        viewModel.nextPage()
                    }
                }
            })

           // adapterImage.submitList(list)
            adapter = adapterImage
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.options_1_img -> changeRowCount(ROW_1)

            R.id.options_2_img -> changeRowCount(ROW_2)

            R.id.options_3_img -> changeRowCount(ROW_3)

            R.id.options_4_img -> changeRowCount(ROW_4)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun changeRowCount(spanCount: Int) {
        manager.spanCount = spanCount
        adapterImage.notifyDataSetChanged()
    }
   /* fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
*/
}
