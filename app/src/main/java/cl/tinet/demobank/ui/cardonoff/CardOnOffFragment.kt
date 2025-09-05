package cl.tinet.demobank.ui.cardonoff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cl.tinet.demobank.data.model.Card
import cl.tinet.demobank.databinding.FragmentCardOnoffBinding
import cl.tinet.demobank.ui.cardonoff.adapter.CardToggleAdapter
import cl.tinet.demobank.ui.cardonoff.presentation.CardOnOffContract
import cl.tinet.demobank.ui.cardonoff.presentation.CardOnOffNavigator
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CardOnOffFragment : Fragment(), CardOnOffContract.View {

    private var _binding: FragmentCardOnoffBinding? = null
    private val binding get() = _binding!!
    
    @Inject
    lateinit var presenter: CardOnOffContract.Presenter
    
    @Inject
    lateinit var navigator: CardOnOffNavigator
    
    private lateinit var cardToggleAdapter: CardToggleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardOnoffBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListeners()
        navigator.setFragment(this)
        presenter.attachView(this)
        presenter.initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbindView()
        _binding = null
    }
    
    private fun setupRecyclerView() {
        cardToggleAdapter = CardToggleAdapter { card, isEnabled ->
            presenter.onCardToggleChanged(card, isEnabled)
        }
        
        binding.rvCards.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cardToggleAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.tvClose.setOnClickListener {
            presenter.onCloseClicked()
        }
    }
    
    // CardOnOffContract.View implementation
    override fun showCards(cards: List<Card>) {
        cardToggleAdapter.updateCards(cards)
    }
    
    override fun showLoading() {
        // Implement loading state if needed
    }
    
    override fun hideLoading() {
        // Implement hide loading if needed
    }
    
    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    
    override fun closeView() {
        navigator.closeCardOnOff()
    }
}