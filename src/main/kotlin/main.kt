import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.*
import java.io.File
import javax.swing.text.DefaultEditorKit

// Some keys related APIs are still an experimental feature of Compose, and later API changes are possible.
@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    val logo = painterResource("compose-logo.png")
    val text = remember { mutableStateOf(TextFieldValue(text = "Type some text here...")) }

    Tray(icon = logo, menu = {
        Item("Quit App", onClick = ::exitApplication)
    })

    Window(onCloseRequest = ::exitApplication, title = "Compose Demo") {
        MenuBar {
            Menu("File") {
                Item("New", onClick = {}, shortcut = KeyShortcut(key = Key.N, meta = true))
                Item("Open", onClick = {})
                Item("Save", onClick = {})
                Separator()
                // Key shortcut in menu
                Menu("Submenu") {
                    Item("Item #1", onClick = {})
                    Item("Item #2", onClick = {})
                }
                Item("Exit", onClick = ::exitApplication, shortcut = KeyShortcut(Key.Escape))
            }
            Menu("Actions") {
                Item("Cut", onClick = {}, shortcut = KeyShortcut(key = Key.C, meta = true))
                Item("Copy", onClick = {}, shortcut = KeyShortcut(key = Key.C, meta = true))
                Item("Paste", onClick = {}, shortcut = KeyShortcut(key = Key.V, meta = true))
                Separator()
                Item(
                    text = "Delete all",
                    onClick = { text.value = TextFieldValue("") },
                    shortcut = KeyShortcut(key = Key.D, shift = true, ctrl = true)
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            BasicTextField(
                value = text.value,
                onValueChange = { text.value = it },
                // Key shortcut without menu
                modifier = Modifier.onPreviewKeyEvent {
                    when (it.key) {
                        Key.Tab -> {
                            // Add 4 spaces when tab is pressed and reposition cursor
                            val newText = text.value.text + "    "
                            val length = newText.length
                            text.value = TextFieldValue(text = newText, selection = TextRange(length, length))
                            true
                        }
                        else -> false
                    }
                }
            )
        }
    }
}
