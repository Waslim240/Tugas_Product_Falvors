package kelompoktiga.perpustakaan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kelompoktiga.perpustakaan.R
import kelompoktiga.perpustakaan.room.peminjaman.Peminjaman
import kotlinx.android.synthetic.gratis.item_layout_buku2.view.*

class PinjamAdapter(private var onClick : (Peminjaman)->Unit) : RecyclerView.Adapter<PinjamAdapter.ViewHolder>() {

    private var dataBuku : List<Peminjaman>? = null

    fun setDataFilm(buku : List<Peminjaman>){
        this.dataBuku = buku
    }
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_buku2, parent, false)
        return ViewHolder(viewItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvJudul.text = "Judul : ${dataBuku!![position].judul}"
        holder.itemView.tvTglRilis.text = "Rilis : ${dataBuku!![position].tanggalRilis}"
        holder.itemView.tvPenulis.text = "Penulis : ${dataBuku!![position].penulis}"
        holder.itemView.deadline.text = "Deadline Pengembalian : ${dataBuku!![position].deadline}"

        Glide.with(holder.itemView.context).load(dataBuku!![position].sampul).apply(RequestOptions().override(120, 120)).into(holder.itemView.imageBuku)


        holder.itemView.cardDetailFilm.setOnClickListener{
            onClick(dataBuku!![position])
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
}