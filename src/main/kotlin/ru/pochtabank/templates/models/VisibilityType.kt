package ru.pochtabank.templates.models

enum class VisibilityType() {
    INTERNAL_ALL,
    PUBLIC_FACTORY,
    PUBLIC_ALL;

    fun getClassVisibility(): String {
        return when (this) {
            INTERNAL_ALL -> "internal "
            PUBLIC_FACTORY -> "internal "
            PUBLIC_ALL -> ""
        }
    }

//    override fun toString(): String {
//        return when (this) {
//            INTERNAL -> "Доступ только внутри модуля"
//            INTERNAL_FACTORY -> "Доступ извне модуля через Screen Factory"
//            PUBLIC -> "Открут полный доступ"
//        }
//    }
}