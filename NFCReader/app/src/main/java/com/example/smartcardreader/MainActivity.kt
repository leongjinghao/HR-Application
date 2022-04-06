package com.example.smartcardreader

import android.graphics.Color
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    public override fun onResume() {
        super.onResume()
        nfcAdapter?.enableReaderMode(this, this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null)
    }

    public override fun onPause() {
        super.onPause()
        nfcAdapter?.disableReaderMode(this)
    }

    override fun onTagDiscovered(tag: Tag?) {
        val isoDep = IsoDep.get(tag)
        val txtView = findViewById(R.id.textView) as TextView
        isoDep.connect()
        val response = isoDep.transceive(Utils.hexStringToByteArray("00A4040007A0000002471001"))
        runOnUiThread {
            //txtView.text = ("\nCard Response: " + Utils.toHex(response))
            if(Utils.toHex(response) == "9000") {
                txtView.text = "Success!"
                txtView.setTextColor(Color.GREEN)
            }
            else{
                txtView.text = "Fail!"
                txtView.setTextColor(Color.RED)
            }

        }
        isoDep.close()
    }
}