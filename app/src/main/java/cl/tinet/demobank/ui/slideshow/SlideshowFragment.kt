package cl.tinet.demobank.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cl.tinet.demobank.databinding.FragmentSlideshowBinding
import cl.tinet.demobank.ui.slideshow.presentation.SlideshowContract
import cl.tinet.demobank.ui.slideshow.presentation.SlideshowNavigator
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SlideshowFragment : Fragment(), SlideshowContract.View {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!
    
    @Inject
    lateinit var presenter: SlideshowContract.Presenter
    
    @Inject
    lateinit var navigator: SlideshowNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
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
    
    // SlideshowContract.View implementation
    override fun showSlideshowText(text: String) {
        binding.textSlideshow.text = text
    }
    
    override fun showLoading() {
        // Implement loading state if needed
    }
    
    override fun hideLoading() {
        // Implement hide loading if needed
    }
}