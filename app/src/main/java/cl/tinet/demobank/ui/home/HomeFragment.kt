package cl.tinet.demobank.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cl.tinet.demobank.data.model.Account
import cl.tinet.demobank.databinding.FragmentHomeBinding
import cl.tinet.demobank.ui.home.adapter.AccountsAdapter
import cl.tinet.demobank.ui.home.presentation.HomeContract
import cl.tinet.demobank.ui.home.presentation.HomeNavigator
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomeFragment : Fragment(), HomeContract.View {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    @Inject
    lateinit var presenter: HomeContract.Presenter
    
    @Inject
    lateinit var navigator: HomeNavigator
    
    private lateinit var accountsAdapter: AccountsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
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
        accountsAdapter = AccountsAdapter { account ->
            navigator.navigateToAccountDetail(account.id)
        }
        
        binding.rvAccounts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = accountsAdapter
        }
    }
    
    // HomeContract.View implementation
    override fun showHomeText(text: String) {
        binding.textHome.text = text
    }
    
    override fun showAccounts(accounts: List<Account>) {
        accountsAdapter.updateAccounts(accounts)
    }
    
    override fun showLoading() {
        // Implement loading state if needed
    }
    
    override fun hideLoading() {
        // Implement hide loading if needed
    }
}