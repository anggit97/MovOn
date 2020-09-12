package com.eoa.tech.core.navigation

import android.content.Context
import android.content.Intent


/**
 * Created by Anggit Prayogo on 02,August,2020
 * GitHub : https://github.com/anggit97
 */
object Actions {
    /**
     * Auth
     */
    fun loginIntent() = Intent("action.auth.login")


    /**
     * Invest
     */
    fun investDetailIntent() = Intent("action.invest.detail")
    fun investDetailList() = Intent("action.invest.list")

    /**
     * Payment
     */
    fun paymentCheckoutGold() = Intent("action.payment.checkout_gold")
    fun paymentChooseGoldPieces() = Intent("action.payment.choose_gold_pieces")
    fun paymentCheckoutMoney() = Intent("action.payment.checkout_money")
    fun paymentChoosePaymentMethod() = Intent("action.payment.choose_payment_method")

    /**
     * App
     */
    fun mainIntent() = Intent("action.app.main")

    fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)
}