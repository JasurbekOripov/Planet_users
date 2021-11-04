package uz.juo.planetusers.ui.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.FadingCircle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.juo.planetusers.R
import uz.juo.planetusers.adapters.HomeRvAdapter
import uz.juo.planetusers.adapters.UserAdapter
import uz.juo.planetusers.databinding.FragmentMainBinding
import uz.juo.planetusers.models.Data
import uz.juo.planetusers.room.dao.UserDao
import uz.juo.planetusers.room.entity.UserEntity
import uz.juo.planetusers.utils.NetworkHelper
import java.lang.Exception

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    private var param1: String? = null
    private var param2: String? = null
    lateinit var userAdapter: UserAdapter
    val mainViewModel: MainViewModel by viewModels()
    lateinit var db: UserDao
    lateinit var mainAdapter: HomeRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        var root = binding.root
        db = mainViewModel.db()
        loadData()
        binding.refresh.setOnClickListener {
            binding.spinKit.visibility = View.VISIBLE
            getFromRetrofit()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        // loadData
    }
    private fun loadData() {
        if (db.getAll().isEmpty()) {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dialogBtn_cancel = dialog.findViewById(R.id.deny) as TextView
            dialogBtn_cancel.setOnClickListener {
                view?.let { it1 ->
                    Snackbar.make(
                        it1,
                        "For update data click the refresh",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                dialog.cancel()
            }
            val dialogBtn_okay = dialog.findViewById(R.id.grant) as TextView
            dialogBtn_okay.setOnClickListener {
                getFromRetrofit()
                dialog.cancel()
            }
            dialog.setCancelable(false)
            dialog.show()
        } else {
            loadFromDb()
        }
    }

    private fun loadFromDb() {
        val list = db.getAll()
        if (list.isEmpty()) {
            Toast.makeText(requireContext(), "Empty list", Toast.LENGTH_SHORT).show()
        }
        userAdapter = UserAdapter(requireContext(),list as ArrayList<UserEntity>, object : UserAdapter.itemClick1 {
            override fun setOnClick1(data: UserEntity, position: Int) {
                findNavController().navigate(R.id.infoFragment, bundleOf(Pair("data", data)))
            }

            override fun setOnEditClick1(data: UserEntity, position: Int) {
                findNavController().navigate(R.id.editFragment, bundleOf(Pair("data", data)))
            }

            override fun setOnDeleteClick1(data: UserEntity, position: Int) {
                db.deleteById(data.id)
                userAdapter.notifyItemRemoved(position)
                list.remove(data)
                userAdapter.notifyItemRangeRemoved(position, list.size)
                view?.let {
                    Snackbar.make(
                        it,
                        "${data.first_name} is deleted",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })
        binding.rv.adapter = userAdapter
        binding.spinKit.visibility = View.INVISIBLE
    }

    private fun getFromRetrofit() {
        var networkHelper = NetworkHelper(requireContext())

        if (networkHelper.isNetworkConnected()) {

            val progressBar = binding.spinKit as ProgressBar
            val doubleBounce: Sprite = FadingCircle()
            binding.spinKit.visibility = View.VISIBLE
            progressBar.indeterminateDrawable = doubleBounce
            mainAdapter = HomeRvAdapter(object : HomeRvAdapter.itemClick {
                override fun setOnClick(data: Data, position: Int) {

                }

                override fun setOnEditClick(data: Data, position: Int) {

                }

                override fun setOnDeleteClick(data: Data, position: Int) {

                }

                override fun dataListener(data: Data, position: Int) {

                }
            })
            mainViewModel.users().observe(viewLifecycleOwner, {
                lifecycleScope.launch {
                    mainAdapter.submitData(it)
                }
            })
            mainAdapter.addLoadStateListener {
                var info = mainAdapter.snapshot()
                for (i in info) {
                    if (i != null) {
                        db.add(UserEntity(i.avatar, i.email, i.first_name, i.id, i.last_name))
                    }
                }
                if (info.size > 12)
                    loadFromDb()
            }
        } else {
            view?.let { Snackbar.make(it, "No Internet Connection", Snackbar.LENGTH_SHORT).show() }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}