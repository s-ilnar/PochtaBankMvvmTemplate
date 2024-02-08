package ru.pochtabank.templates.core.mvvm.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf
import ru.pochtabank.templates.core.utils.getRouteStateModelName
import ru.pochtabank.templates.core.utils.getUiModelsPackageName
import ru.pochtabank.templates.core.utils.getUiStateModelName
import ru.pochtabank.templates.models.BuildSettings

fun blankViewModelKt(
    buildSettings: BuildSettings,
) = """package ${escapeKotlinIdentifier(buildSettings.packageName)}
    
    
${
    if (buildSettings.isGenerateUiStateModel && buildSettings.isGenerateRoutingStateModel) {
        """import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.pochtabank.data.models.SingleLiveEvent"""
    } else if (buildSettings.isGenerateRoutingStateModel) {
        """import androidx.lifecycle.LiveData
import ru.pochtabank.data.models.SingleLiveEvent"""
    } else if (buildSettings.isGenerateUiStateModel) {
        """import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData"""
    } else {
        """"""
    }
}
import ru.pochtabank.ui.base.mvvm.BaseViewModel
${renderIf (buildSettings.isGenerateUiStateModel) { 
    """import ${getUiModelsPackageName(buildSettings.packageName)}.${getUiStateModelName(buildSettings.entityName)}""" 
}}
${renderIf (buildSettings.isGenerateRoutingStateModel) { 
    """import ${getUiModelsPackageName(buildSettings.packageName)}.${getRouteStateModelName(buildSettings.entityName)}"""
}}


${buildSettings.visibilityType.getClassVisibility()}class ${buildSettings.entityName}ViewModel() : BaseViewModel() {
    ${renderIf (buildSettings.isGenerateUiStateModel) {
        """private val viewStateData = MutableLiveData<${getUiStateModelName(buildSettings.entityName)}>()
    val viewState: LiveData<${getUiStateModelName(buildSettings.entityName)}> = viewStateData"""
    }}
    ${renderIf (buildSettings.isGenerateRoutingStateModel) {
        """private val routeStateData = SingleLiveEvent<${getRouteStateModelName(buildSettings.entityName)}>()
    val routeState: LiveData<${getRouteStateModelName(buildSettings.entityName)}> = routeStateData"""
    }}
    

    init {
        loadData()
    }

    private fun loadData() {
        //TODO(Необходимо реализовать получение данных и дальнейшее отображение на экране)
        //stopship
    }
}
"""