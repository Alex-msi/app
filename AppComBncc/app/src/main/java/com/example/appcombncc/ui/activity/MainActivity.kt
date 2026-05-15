package com.example.appcombncc.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.appcombncc.R
import com.example.appcombncc.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.example.appcombncc.util.SessionManager

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var sessionManager: SessionManager

    private val googleLoginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        val data: Intent? = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)

            Toast.makeText(
                this,
                "Autenticação com Google realizada com sucesso",
                Toast.LENGTH_SHORT
            ).show()

            val email = account?.email.orEmpty()
            val uid = account?.id.orEmpty()
            val nome = account?.displayName.orEmpty()

            sessionManager.salvarSessaoAutenticada(
                googleUid = uid,
                email = email,
                nome = nome
            )
            abrirListaAutenticada(email)

        } catch (e: ApiException) {
            val message = when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    "Login com Google cancelado"
                }

                CommonStatusCodes.NETWORK_ERROR -> {
                    "Erro de conexão com a internet"
                }

                else -> {
                    "Falha na autenticação com Google. Código: ${e.statusCode}"
                }
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        configurarLoginGoogle()
        configurarNavegacao()
        configurarMenuToolbar()
        aplicarRegraSessaoNaInicializacao()
    }

    private fun configurarLoginGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(
            this,
            googleSignInOptions
        )
    }

    private fun configurarNavegacao() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment)
        )

        binding.topAppBar.setupWithNavController(
            navController,
            appBarConfiguration
        )
    }


    private fun aplicarRegraSessaoNaInicializacao() {
        val sessaoValida = sessionManager.sessaoValidaAgora()

        if (sessaoValida) {
            // Sessão local válida: mantém usuário autenticado para uso offline,
            // mas preserva Home como entrada pública do app.
            return
        }

        val contaGoogleAtual = GoogleSignIn.getLastSignedInAccount(this)
        if (contaGoogleAtual != null) {
            val email = contaGoogleAtual.email.orEmpty()
            if (email.isNotBlank()) {
                // Sincroniza sessão local sem redirecionar automaticamente.
                sessionManager.salvarSessaoAutenticada(
                    googleUid = contaGoogleAtual.id.orEmpty(),
                    email = email,
                    nome = contaGoogleAtual.displayName.orEmpty()
                )
                return
            }
        }

        // Sessão expirada ou revogada: limpar estado local e manter funcionalidades públicas.
        sessionManager.limparSessao()
    }

    private fun configurarMenuToolbar() {
        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.action_home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.action_login -> {
                    autenticarComGoogle()
                    true
                }

                else -> false
            }
        }
    }

    private fun autenticarComGoogle() {
        val contaAtual = GoogleSignIn.getLastSignedInAccount(this)

        if (contaAtual != null) {
            Toast.makeText(
                this,
                "Usuário já autenticado com Google",
                Toast.LENGTH_SHORT
            ).show()

            val email = contaAtual.email.orEmpty()
            sessionManager.salvarSessaoAutenticada(
                googleUid = contaAtual.id.orEmpty(),
                email = email,
                nome = contaAtual.displayName.orEmpty()
            )
            abrirListaAutenticada(email)
        } else {
            googleLoginLauncher.launch(googleSignInClient.signInIntent)
        }
    }


    private fun abrirListaAutenticada(email: String) {
        val bundle = Bundle().apply { putString("email_usuario", email) }

        if (navController.currentDestination?.id == R.id.listaAutenticadaFragment) {
            navController.popBackStack()
        }

        navController.navigate(R.id.listaAutenticadaFragment, bundle)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }
}