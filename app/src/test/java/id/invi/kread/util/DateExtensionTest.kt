package id.invi.kread.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.Calendar
import java.util.Locale

class DateExtensionTest {

    @Test
    fun `Date formatToDisplay should return formatted string`() {
        val calendar = Calendar.getInstance().apply {
            set(2026, Calendar.APRIL, 14)
        }
        val date = calendar.time
        
        // Use Locale.US to ensure consistent formatting for the test
        Locale.setDefault(Locale.US)
        assertEquals("14 Apr 2026", date.formatToDisplay())
    }

    @Test
    fun `Date formatToServer should return formatted string`() {
        val calendar = Calendar.getInstance().apply {
            set(2026, Calendar.APRIL, 14)
        }
        val date = calendar.time
        
        assertEquals("2026-04-14", date.formatToServer())
    }

    @Test
    fun `String toDate should return correct Date object`() {
        Locale.setDefault(Locale.US)
        val dateString = "14 Apr 2026"
        val date = dateString.toDate()
        
        val calendar = Calendar.getInstance().apply {
            if (date != null) {
                time = date
            }
        }
        
        assertEquals(2026, calendar.get(Calendar.YEAR))
        assertEquals(Calendar.APRIL, calendar.get(Calendar.MONTH))
        assertEquals(14, calendar.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun `String toDate with invalid format should return null`() {
        val invalidDateString = "2026-04-14"
        assertNull(invalidDateString.toDate())
    }

    @Test
    fun `String serverToDate should return correct Date object`() {
        val dateString = "2026-04-14"
        val date = dateString.serverToDate()
        
        val calendar = Calendar.getInstance().apply {
            if (date != null) {
                time = date
            }
        }
        
        assertEquals(2026, calendar.get(Calendar.YEAR))
        assertEquals(Calendar.APRIL, calendar.get(Calendar.MONTH))
        assertEquals(14, calendar.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun `String serverToDate with invalid format should return null`() {
        val invalidDateString = "14 Apr 2026"
        assertNull(invalidDateString.serverToDate())
    }

    @Test
    fun `String formatTime should return HH mm formatted string`() {
        val timeString = "14:30:45"
        assertEquals("14:30", timeString.formatTime())
    }

    @Test
    fun `String formatTime with invalid format should return original string`() {
        val invalidTimeString = "invalid_time"
        assertEquals(invalidTimeString, invalidTimeString.formatTime())
    }
}
