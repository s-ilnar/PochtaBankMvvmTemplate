package ru.pochtabank.templates.viewModelFragment.builders

import com.android.tools.idea.wizard.template.BooleanParameter
import com.android.tools.idea.wizard.template.Constraint
import com.android.tools.idea.wizard.template.booleanParameter
import com.android.tools.idea.wizard.template.enumParameter
import com.android.tools.idea.wizard.template.fragmentToLayout
import com.android.tools.idea.wizard.template.stringParameter
import ru.pochtabank.templates.models.VisibilityType

val entityName = stringParameter {
    name = "Название сущности"
    default = ""
    help =
        "Название сущности/фичи (например, Timeline -> TimelineFragment, TimelineViewModel)"
    constraints = listOf(Constraint.NONEMPTY)
}

val layoutName = stringParameter {
    name = "Название layout"
    default = ""
    help = "Название лаяута"
    constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
    suggest = { fragmentToLayout(entityName.value.lowercase()) }
}

val generateRoutingModel: BooleanParameter = booleanParameter {
    name = "Добавить состояние роутинга"
    default = true
    help = "Добавляет базовую модель роутинга в Fragment и ViewModel"
}

val generateUiStateModel: BooleanParameter = booleanParameter {
    name = "Добавить состояние UI"
    default = true
    help = "Добавляет базовую модель роутинга в Fragment и ViewModel"
}

val visibilityType = enumParameter<VisibilityType> {
    name = "Доступ к сущности"
    default = VisibilityType.INTERNAL_ALL
    help = "Управление модификаторами доступа, создаваемых сущностей. По умолчанию, скрыто внутри модуля (internal)."
}