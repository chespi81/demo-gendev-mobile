package cl.tinet.demobank.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.tinet.demobank.R
import cl.tinet.demobank.data.model.Account

class AccountsAdapter(
    private var accounts: List<Account> = emptyList(),
    private val onAccountClick: (Account) -> Unit
) : RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {

    fun updateAccounts(newAccounts: List<Account>) {
        accounts = newAccounts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(accounts[position])
    }

    override fun getItemCount(): Int = accounts.size

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val accountType: TextView = itemView.findViewById(R.id.tv_account_type)
        private val accountBalance: TextView = itemView.findViewById(R.id.tv_account_balance)
        private val chevron: View = itemView.findViewById(R.id.iv_chevron)

        fun bind(account: Account) {
            accountType.text = account.type
            accountBalance.text = "${account.currency}${String.format("%,.2f", account.balance)}"
            
            itemView.setOnClickListener {
                onAccountClick(account)
            }
        }
    }
}