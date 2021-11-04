package uz.juo.planetusers.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import uz.juo.planetusers.databinding.FragmentEditBinding
import uz.juo.planetusers.room.entity.UserEntity
import uz.juo.planetusers.ui.main.MainViewModel
import java.lang.Exception

private const val ARG_PARAM1 = "data"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class EditFragment : Fragment() {
    lateinit var binding: FragmentEditBinding
    private var param1: UserEntity? = null
    private var param2: String? = null
    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as UserEntity
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        var root = binding.root
        try {
            binding.apply {
                nameTv.text = param1?.first_name
                fNameEt.setText(param1?.first_name)
                sNameEt.setText(param1?.last_name)
                emailEt.setText(param1?.email)
                Picasso.get().load(param1?.avatar).into(img)
                save.setOnClickListener {
                    var user_email = emailEt.text.toString()
                    var fName_user = fNameEt.text.toString()
                    var sName_user = sNameEt.text.toString()
                    if (user_email != "" && fName_user != "" && sName_user != "") {
                        var user = UserEntity(
                            param1?.avatar.toString(),
                            user_email,
                            fName_user,
                            param1?.id ?: 1,
                            sName_user
                        )
                        mainViewModel.db().add(user)
                        findNavController().popBackStack()
                    } else {
                        view?.let { it1 ->
                            Snackbar.make(it1, "Please fill all information", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}