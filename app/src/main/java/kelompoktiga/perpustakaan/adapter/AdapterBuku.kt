package kelompoktiga.perpustakaan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kelompoktiga.perpustakaan.R
import kelompoktiga.perpustakaan.model.BukuAllResponseItem
import kotlinx.android.synthetic.gratis.item_layout_buku.view.*

@SuppressLint("SetTextI18n")
class AdapterBuku(private var onclick : (BukuAllResponseItem) -> Unit) : RecyclerView.Adapter<AdapterBuku.ViewHolder> () {

    private var dataBuku: List<BukuAllResponseItem>? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterBuku.ViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_buku, parent, false)
        return ViewHolder(layout)
    }


    override fun onBindViewHolder(holder: AdapterBuku.ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(dataBuku!![position].sampul).into(holder.itemView.imageBuku)
        holder.itemView.tvJudul.text = "Judul : ${dataBuku!![position].judul}"
        holder.itemView.tvTglRilis.text = "Rilis : ${dataBuku!![position].tanggalRilis}"
        holder.itemView.tvPenulis.text = "Penulis : ${dataBuku!![position].penulis}"

        holder.itemView.cardDetailFilm.setOnClickListener {
            onclick(dataBuku!![position])
        }
    }

    override fun getItemCount(): Int {
        return when (dataBuku) {
            null -> {
                0
            }
            else -> {
                dataBuku!!.size
            }
        }
    }

    fun setDataFilm(buku: List<BukuAllResponseItem>) {
        this.dataBuku = buku
    }

}