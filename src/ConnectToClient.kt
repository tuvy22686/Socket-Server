import java.io.*
import java.net.Socket

class ConnectToClient(private val socket: Socket, private val id: Int): Thread() {

    private lateinit var inputStream: InputStream
    private lateinit var bufferedReader: BufferedReader
    private lateinit var printWriter: PrintWriter

    override fun run() {
        try {
            inputStream = socket.getInputStream()
            bufferedReader = BufferedReader(InputStreamReader(inputStream))
            printWriter = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream())))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            while (inputStream.available() == 0) {}

            val received = bufferedReader.readLine()
            println("received data is $received")
            printWriter.println("[From Server] Received data is {$received}")
            printWriter.flush()
        } catch (e: Exception) {
            e.printStackTrace()

            try {
                close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        println("Bye $id")
    }

    private fun close() {
        bufferedReader.close()
        printWriter.close()
        inputStream.close()
        socket.close()
    }
}