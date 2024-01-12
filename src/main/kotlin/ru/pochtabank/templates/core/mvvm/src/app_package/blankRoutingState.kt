package ru.pochtabank.templates.viewModelFragment.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun blankRoutingStateKt(
    className: String,
    packageName: String,
): String {
    return """package ${escapeKotlinIdentifier(packageName)}

internal sealed class $className {
    //Реализацию писать внутри фигурных скобок!
}
"""
}