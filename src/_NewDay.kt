import java.io.File
import java.nio.file.Paths

fun main() {
    val absPath = Paths.get("").toAbsolutePath().toString() + "\\src"
    val templatePath = "$absPath\\template.kt"
    println("Insert day number")
    var dayNumber = readln()
    if (dayNumber.length < 2) dayNumber = "0$dayNumber"
    File(templatePath).copyTo(File("$absPath\\Day$dayNumber.kt"))

    val newDayFile = File("$absPath\\Day$dayNumber.kt")
    newDayFile.writeText(newDayFile.readText().replace("%DAY%", "Day$dayNumber"))

    File("$absPath\\Day$dayNumber.txt").printWriter().use { out ->
        out.println("")
    }
    File("$absPath\\Day$dayNumber" + "_test.txt").printWriter().use { out ->
        out.println("")
    }
}