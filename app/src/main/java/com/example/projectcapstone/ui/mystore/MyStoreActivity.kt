package com.example.projectcapstone.ui.mystore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone.R
import com.example.projectcapstone.adapter.MyStoreAdapter
import com.example.projectcapstone.databinding.ActivityMyStoreBinding
import com.example.projectcapstone.databinding.FragmentProfileBinding
import com.example.projectcapstone.model.UserPreference
import com.example.projectcapstone.network.response.Product
import com.example.projectcapstone.ui.AddProductActivity
import com.example.projectcapstone.ui.ProductDetail
import com.example.projectcapstone.ui.cart.CartViewModel
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class MyStoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyStoreBinding
    private lateinit var myStoreViewModel: MyStoreViewModel
    private lateinit var userId : String
    private lateinit var username : String
    private lateinit var yourProducts : List<Product>
    private lateinit var access_token : String
    private var itembool : Boolean = false
    private lateinit var rv_store: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rv_store = binding.rvStore
        myStoreViewModel =  ViewModelProvider(this).get(MyStoreViewModel::class.java)

        val userPref = UserPreference.getInstance(dataStore)
        lifecycleScope.launch {
            access_token= userPref.getPreferences()[UserPreference.TOKEN_KEY].toString()
            access_token = "Bearer "+ access_token
            username = userPref.getPreferences()[UserPreference.NAME_KEY].toString()
            userId = userPref.getPreferences()[UserPreference.USERID_KEY].toString()
        }
        binding.tvDetailName.text = username

        getyourProducts()
        myStoreViewModel.yourProducts.observe(this){
            yourProducts = it!!
            ActivityStore(yourProducts)
        }
        binding.floatBtnadd.setOnClickListener{
            startActivity(Intent(this, AddProductActivity::class.java))
        }
    }

    fun getyourProducts(){
        myStoreViewModel.getStoreProducts(access_token, userId)
    }

    fun ActivityStore(item: List<Product>){
        rv_store.layoutManager = GridLayoutManager(applicationContext, 2)
        val adapter = MyStoreAdapter(item)
        rv_store.adapter = adapter
        adapter.setOnItemClickCallback(object : MyStoreAdapter.OnItemClickCallback{
            override fun onItemClicked(item: Product) {
                val intent = Intent(this@MyStoreActivity, ProductDetail::class.java)
                intent.putExtra("product", item)
                System.out.println("getclicked")
                startActivity(intent)
            }
            override fun onLongItemClicked(item: Product) {
                myStoreViewModel.deleteStoreProduct(access_token, item.sellerId, item.id)
                myStoreViewModel.item.observe(this@MyStoreActivity){
                    itembool = it!!
                    if (itembool){
                        getyourProducts()
                    }
                }
            }

        })

    }
}
