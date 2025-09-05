package cl.tinet.demobank.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cl.tinet.demobank.databinding.FragmentGalleryBinding
import cl.tinet.demobank.ui.gallery.presentation.GalleryContract
import cl.tinet.demobank.ui.gallery.presentation.GalleryNavigator
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class GalleryFragment : Fragment(), GalleryContract.View {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    
    @Inject
    lateinit var presenter: GalleryContract.Presenter
    
    @Inject
    lateinit var navigator: GalleryNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator.setFragment(this)
        presenter.attachView(this)
        presenter.initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbindView()
        _binding = null
    }
    
    // GalleryContract.View implementation
    override fun showGalleryText(text: String) {
        binding.textGallery.text = text
    }
    
    override fun showLoading() {
        // Implement loading state if needed
    }
    
    override fun hideLoading() {
        // Implement hide loading if needed
    }
}