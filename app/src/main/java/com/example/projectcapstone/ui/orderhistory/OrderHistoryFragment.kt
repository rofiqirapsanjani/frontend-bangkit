package com.example.projectcapstone.ui.orderhistory

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone.R
import com.example.projectcapstone.adapter.OrderHistoryAdapter
import com.example.projectcapstone.databinding.FragmentOrderHistoryBinding
import com.example.projectcapstone.model.UserPreference
import com.example.projectcapstone.network.response.OrderResponse
import kotlinx.coroutines.launch
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class OrderHistoryFragment : Fragment() {

    private lateinit var userId : String
    private lateinit var access_token : String
    private lateinit var hist:List<OrderResponse>
    private lateinit var rv_history: RecyclerView
    private lateinit var orderHistoryViewModel: OrderHistoryViewModel
    private lateinit var binding: FragmentOrderHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)
        rv_history = view.findViewById(R.id.rv_history)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        orderHistoryViewModel = ViewModelProvider(this).get(OrderHistoryViewModel::class.java)
        val userPref = UserPreference.getInstance(requireActivity().dataStore)

        lifecycleScope.launch {
            access_token= userPref.getPreferences()[UserPreference.TOKEN_KEY].toString()
            access_token = "Bearer "+ access_token
            userId = userPref.getPreferences()[UserPreference.USERID_KEY].toString()
        }

        getAllHistory(access_token, userId)
        orderHistoryViewModel.hist.observe(viewLifecycleOwner){
            hist = it!!
            ActivityHistoryOrders(hist)
        }
    }

    fun getAllHistory(access_token: String, userId:String) {
        orderHistoryViewModel.getAllhistory(access_token, userId)
    }

    fun ActivityHistoryOrders(histories: List<OrderResponse>){
        rv_history.layoutManager = LinearLayoutManager(activity)
        val adapter = OrderHistoryAdapter(histories)
        rv_history.adapter = adapter
        adapter.setOnItemClickCallback(object : OrderHistoryAdapter.OnItemClickCallback{
            override fun onItemClicked(hist: OrderResponse) {
                System.out.println("getclicked")
            }
            override fun onLongItemClicked(history: OrderResponse) {
                System.out.println("Long click")
            }
        })
    }
}