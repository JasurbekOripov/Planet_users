package uz.juo.planetusers.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import uz.juo.planetusers.R
import uz.juo.planetusers.databinding.FragmentInfoBinding
import uz.juo.planetusers.room.entity.UserEntity

private const val ARG_PARAM1 = "data"
private const val ARG_PARAM2 = "param2"

class InfoFragment : Fragment() {
    lateinit var binding: FragmentInfoBinding
    private var param1: UserEntity? = null
    private var param2: String? = null

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
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        binding.apply {
            Picasso.get().load(param1?.avatar).into(image)
            fName.text = param1?.first_name
            sName.text = param1?.last_name
            email.text = param1?.email
            nameTv.text = param1?.first_name
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            editTv.setOnClickListener {
                findNavController().popBackStack()
                findNavController().navigate(R.id.editFragment, bundleOf(Pair("data", param1)))
            }

        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}