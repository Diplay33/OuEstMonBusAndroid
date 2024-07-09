package model.helpers

fun String.toASCIIDecimal(): Int {
    var convertedStringValue = ""
    this.forEach {
        if(it.isLetter()) {
            convertedStringValue += it.code
        }
        else {
            convertedStringValue += it
        }
    }
    return convertedStringValue.toIntOrNull() ?: 0
}