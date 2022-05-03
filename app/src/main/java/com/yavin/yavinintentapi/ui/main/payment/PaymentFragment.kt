package com.yavin.yavinintentapi.ui.main.payment

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.yavin.yavinintentapi.databinding.FragmentPaymentBinding
import com.yavin.yavinintentapi.ui.main.model.ApiVersion
import com.yavin.yavinintentapi.ui.main.model.Customer
import com.yavin.yavinintentapi.ui.main.payment.v1.PaymentRequestV1
import com.yavin.yavinintentapi.ui.main.payment.v1.PaymentResponseV1
import com.yavin.yavinintentapi.ui.main.payment.v4.PaymentRequestV4
import com.yavin.yavinintentapi.ui.main.payment.v4.PaymentResponseV4

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val gson = Gson()

    lateinit var apiVersion: ApiVersion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val root = binding.root

        val v1 = binding.v1
        v1.setOnClickListener {
            paymentApiV1()
        }

        val v4 = binding.v4
        v4.setOnClickListener {
            paymentApiV4()
        }

        return root
    }

    private fun paymentApiV4() {
        apiVersion = ApiVersion.V4

        val paymentRequest = PaymentRequestV4(
            amount = 100,
            customer = Customer("Pierre", "Ghaz", "pierre@yavin.com")
        )

        val jsonData = gson.toJson(paymentRequest)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("yavin://com.yavin.macewindu/v4/payment?data=$jsonData")
        }

        startActivityForResult(intent, REQUEST_CODE_PAYMENT)
    }

    private fun paymentApiV1() {
        apiVersion = ApiVersion.V1

        val paymentRequest = PaymentRequestV1(
            amount = "100",
            currency = "EUR",
            transactionType = "Debit",
            client = "{\"email\":\"pierre@yavin.com\",\"firstName\":\"Pierre\",\"lastName\":\"Ghazal\"}"
        )

        val intent = Intent()
        intent.component =
            ComponentName("com.yavin.macewindu", "com.yavin.macewindu.PaymentActivity")
        intent.putExtra("vendorToken", paymentRequest.vendorToken)
        intent.putExtra("amount", paymentRequest.amount)
        intent.putExtra("currency", paymentRequest.currency)
        intent.putExtra("transactionType", paymentRequest.transactionType)
        intent.putExtra("reference", paymentRequest.reference)
        intent.putExtra("client", paymentRequest.client)

        startActivityForResult(intent, REQUEST_CODE_PAYMENT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data == null) return

        if (requestCode == REQUEST_CODE_PAYMENT) {
            when (apiVersion) {
                ApiVersion.V1 -> {
                    getPaymentResponseV1(data)
                }

                ApiVersion.V4 -> {
                    getPaymentResponseV4(data)
                }
            }
        }
    }

    private fun getPaymentResponseV4(data: Intent) {
        val json = data.extras?.getString("transaction")
        val response = gson.fromJson(json, PaymentResponseV4::class.java)

        Log.d("PaymentFragment", response.toString())
    }


    private fun getPaymentResponseV1(data: Intent) {
        val response = PaymentResponseV1(
            amount = data.getIntExtra("amount", 0),
            cardToken = data.getStringExtra("cardToken"),
            clientTicket = data.getStringExtra("clientTicket"),
            giftAmount = data.getIntExtra("giftAmount", 0),
            reference = data.getStringExtra("reference"),
            signatureRequired = data.getBooleanExtra("signatureRequired", false),
            status = data.getStringExtra("status"),
            totalAmount = data.getIntExtra("totalAmount", 0),
            transactionId = data.getStringExtra("transactionId")
        )

        Log.d("PaymentFragment", response.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_CODE_PAYMENT = 123

        @JvmStatic
        fun newInstance(): PaymentFragment {
            return PaymentFragment()
        }
    }
}