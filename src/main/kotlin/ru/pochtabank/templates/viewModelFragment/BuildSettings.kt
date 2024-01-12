package ru.pochtabank.templates.viewModelFragment

import com.android.tools.idea.wizard.template.ModuleTemplateData

class BuildSettings(
    val moduleData: ModuleTemplateData,
    val packageName: String,
    val entityName: String,
    val layoutName: String,
    val isGenerateUiStateModel: Boolean,
    val isGenerateRoutingStateModel: Boolean,
)