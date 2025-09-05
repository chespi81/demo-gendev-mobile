package cl.tinet.demobank.ui.cardonoff.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import cl.tinet.demobank.R
import cl.tinet.demobank.data.model.Card
import cl.tinet.demobank.data.model.CardType

class CardToggleAdapter(
    private var cards: List<Card> = emptyList(),
    private val onToggleChange: (Card, Boolean) -> Unit
) : RecyclerView.Adapter<CardToggleAdapter.CardToggleViewHolder>() {

    fun updateCards(newCards: List<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardToggleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_toggle, parent, false)
        return CardToggleViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardToggleViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount(): Int = cards.size

    inner class CardToggleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardContainer: RelativeLayout = itemView.findViewById(R.id.rl_card_container)
        private val cardholderName: TextView = itemView.findViewById(R.id.tv_cardholder_name)
        private val cardNumber: TextView = itemView.findViewById(R.id.tv_card_number)
        private val cardType: TextView = itemView.findViewById(R.id.tv_card_type)
        private val toggleSwitch: SwitchCompat = itemView.findViewById(R.id.switch_card_toggle)

        fun bind(card: Card) {
            cardholderName.text = card.cardholderName
            cardNumber.text = card.maskedNumber
            cardType.text = card.cardType.displayName
            toggleSwitch.isChecked = card.isEnabled

            // Set card background color based on type
            val backgroundColor = when (card.cardType) {
                CardType.ATM -> Color.parseColor("#1E88E5")
                CardType.CASH_BACK -> Color.parseColor("#2E7D32")
                CardType.DEBIT -> Color.parseColor("#1565C0")
            }
            cardContainer.setBackgroundColor(backgroundColor)

            // Set toggle listener
            toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
                card.isEnabled = isChecked
                onToggleChange(card, isChecked)
            }
        }
    }
}