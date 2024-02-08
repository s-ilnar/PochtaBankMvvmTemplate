package ru.pochtabank.templates.models

import com.android.tools.idea.wizard.template.ModuleTemplateData
import ru.pochtabank.templates.models.VisibilityType

class BuildSettings(
    val moduleData: ModuleTemplateData,
    val packageName: String,
    val entityName: String,
    val layoutName: String,
    val isGenerateUiStateModel: Boolean,
    val isGenerateRoutingStateModel: Boolean,
    val visibilityType: VisibilityType,
)