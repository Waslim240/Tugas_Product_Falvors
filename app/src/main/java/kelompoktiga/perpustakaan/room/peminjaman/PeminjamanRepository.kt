package kelompoktiga.perpustakaan.room.peminjaman

class PeminjamanRepository(private val dao : PeminjamanDao) {

    suspend fun pinjamRepo(pinjam : Peminjaman) {
        dao.pinjam(pinjam)
    }

    suspend fun getPinjamRepo(username : String) : List<Peminjaman>{
        return dao.getPeminjaman(username)
    }

    suspend fun kembaliRepo(id : Int, username: String) {
        dao.kembali(id, username)
    }
}