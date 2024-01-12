package ru.pochtabank.templates.viewModelFragment.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf
import com.android.tools.idea.wizard.template.underscoreToCamelCase
import ru.pochtabank.templates.core.utils.getRootPackageName
import ru.pochtabank.templates.viewModelFragment.BuildSettings

fun blankFragmentKt(
    buildSettings: BuildSettings,
): String {
    return """package ${escapeKotlinIdentifier(buildSettings.packageName)}

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.pochtabank.ui.base.mvvm.BaseMvvmFragment
import org.koin.core.parameter.parametersOf
import ${getRootPackageName(buildSettings.packageName)}.databinding.${underscoreToCamelCase(buildSettings.layoutName)}Binding

internal class ${buildSettings.entityName}Fragment :
    BaseMvvmFragment<${underscoreToCamelCase(buildSettings.layoutName)}Binding>(${underscoreToCamelCase(buildSettings.layoutName)}Binding::inflate) {

    override val viewModel: ${buildSettings.entityName}ViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ${renderIf (buildSettings.isGenerateUiStateModel) { 
        """subscribeViewState()""" }}
        ${renderIf (buildSettings.isGenerateRoutingStateModel) {
        """subscribeRoutingState()""" }}
    }

    ${renderIf (buildSettings.isGenerateUiStateModel) {
        """private fun subscribeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            //TODO(Реализовать подписку на состояние экрана) 
            //stopship
        }
    }""" }}

    ${renderIf (buildSettings.isGenerateRoutingStateModel) {
    """private fun subscribeRoutingState() {
        viewModel.routeState.observe(viewLifecycleOwner) {
            //TODO(Реализовать подписку на состояние роутинга либо удалить)
            //stopship
        }
    }""" }}

    companion object {
        @JvmStatic
        fun createFragment(bundle: Bundle): Fragment {
            return ${buildSettings.entityName}Fragment().apply {
                arguments = bundle
            }
        }

        @JvmStatic
        fun createExtras(): Bundle {
            return Bundle()
        }
    }
}
"""
}