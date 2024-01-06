package com.hussein.noteappkmm.domain.note

import com.hussein.noteappkmm.presentation.BabyBlueHex
import com.hussein.noteappkmm.presentation.LightGreenHex
import com.hussein.noteappkmm.presentation.RedOrangeHex
import com.hussein.noteappkmm.presentation.RedPinHex
import com.hussein.noteappkmm.presentation.VioletHex
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
)
{
    companion object{
        private val colors = listOf(RedOrangeHex , RedPinHex ,BabyBlueHex ,VioletHex , LightGreenHex)

        fun generateRandomColor() = colors.random() // Generate Random Color
    }
}
