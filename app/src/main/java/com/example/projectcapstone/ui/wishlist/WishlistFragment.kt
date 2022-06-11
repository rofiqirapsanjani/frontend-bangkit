package com.example.projectcapstone.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone.R
import com.example.projectcapstone.adapter.ProductsAdapter
import com.example.projectcapstone.adapter.WishListAdapter
import com.example.projectcapstone.databinding.FragmentHomeBinding
import com.example.projectcapstone.databinding.FragmentWishlistBinding
import com.example.projectcapstone.network.response.WishListResponse
import com.example.projectcapstone.ui.home.HomeViewModel


class WishlistFragment : Fragment() {
    private lateinit var rv_wishlist: RecyclerView
    private lateinit var wishList : ArrayList<WishListResponse>

    private lateinit var wishlistViewModel: WishlistViewModel
    private lateinit var _binding: FragmentWishlistBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val dashboardViewModel =
//            ViewModelProvider(this).get(WishlistViewModel::class.java)
//
//        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//
//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val view = inflater.inflate(R.layout.fragment_wishlist ,container, false)
        rv_wishlist = view.findViewById(R.id.rv_wishlist)
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        wishlistViewModel = ViewModelProvider(this).get(WishlistViewModel::class.java)
//        getAllwishlist()

        val wishlist= listOf(
            WishListResponse("123123123", "123123123", "sepatu", "bagus", "12322.jpg","baju", 20, "blue", 20 , false, ""),
            WishListResponse("123123123", "123123123", "sepatu", "bagus", "12322.jpg","baju", 20, "blue", 20 ,false, ""),
            WishListResponse("123123123", "123123123", "sepatu", "bagus", "12322.jpg","baju", 20, "blue", 20 ,false, "" ),
            WishListResponse("123123123", "123123123", "sepatu", "bagus", "12322.jpg","baju", 20, "blue", 20 ,false, "")
        )
        showWishlist(wishlist)
    }

    fun getAllwishlist() {
        wishlistViewModel.fetchWishes()
        wishList = wishlistViewModel.getWishes()
    }

    fun showWishlist(wishes: List<WishListResponse>){
        rv_wishlist.layoutManager = LinearLayoutManager(activity)
        rv_wishlist.adapter = WishListAdapter(wishes)
    }

}