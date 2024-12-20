package com.recyclens.aboutus

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recyclens.R
import com.recyclens.core.presentation.components.NavigationTopBar
import com.recyclens.core.presentation.designsystem.PrimaryColor
import com.recyclens.core.presentation.designsystem.RecycLensTheme
import com.recyclens.core.presentation.designsystem.SecondaryColor

@Composable
fun AboutUsScreenRoot(
    onNavigateBack: () -> Unit
) {
    AboutUsScreen(
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun AboutUsScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val wojtekEmail = stringResource(id = R.string.wojtek_email)
    val tomekEmail = stringResource(id = R.string.tomek_email)
    val emailTitle = stringResource(id = R.string.recyclens)

    Scaffold(
        topBar = {
            NavigationTopBar(
                title = stringResource(id = R.string.about_us),
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(brush = Brush.verticalGradient(
                        colors = listOf(SecondaryColor, PrimaryColor),
                        startY = -50f,
                    )),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge.copy(
                    brush = Brush.verticalGradient(
                        colors = listOf(SecondaryColor, PrimaryColor),
                        startY = -25f
                    )
                ),
            )
            Text(
                text = stringResource(id = R.string.watchword),
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.size(48.dp))
            Text(
                text = stringResource(id = R.string.about_us_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.size(48.dp))
            Text(
                text = stringResource(id = R.string.contact_question),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.size(16.dp))
            TextButton(
                onClick = {
                    context.sendMail(
                        receiver = wojtekEmail,
                        subject = emailTitle,
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = wojtekEmail,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = PrimaryColor,
                )
            }
            TextButton(
                onClick = {
                    context.sendMail(
                        receiver = tomekEmail,
                        subject = emailTitle,
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = tomekEmail,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = PrimaryColor,
                )
            }
        }
    }
}

private fun Context.sendMail(receiver: String, subject: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(receiver))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    } catch (e: Error) {
        // TODO: Handle this
    }
}

@Preview(showBackground = true)
@Composable
private fun InformationScreenPreview() {
    RecycLensTheme {
        AboutUsScreen(
            onNavigateBack = {}
        )
    }
}