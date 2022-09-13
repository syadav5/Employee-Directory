package com.examples.employeedirectory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.examples.employeedirectory.data.Employee
import com.examples.employeedirectory.service.EmployeeService
import com.examples.employeedirectory.service.Resource
import com.examples.employeedirectory.viewmodels.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    @MockK
    val employeeService: EmployeeService = mockk()

    val sut = MainViewModel(employeeService)

    val e1 = Employee("", "Azi", "", "", "", "", "")


    private val successResponse: Resource<List<Employee>?> = Resource.Success(
        listOf(
            e1,
            e1.copy(fullName = "Nadia")
        )
    )
    private val errorResponse: Resource<List<Employee>?> = Resource.Error(
        errorCode = "500", errorMsg = "Internal Error Occured"
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `test retrieveEmployeeList should make service call for forced refresh`() {
        runTest {
            coEvery { employeeService.getEmployees() } returns successResponse
            sut.retrieveEmployeeList(true)
            Assert.assertEquals(sut.employeeListLiveData.value?.status, Resource.Status.SUCCESS)
            Assert.assertEquals(sut.employeeListLiveData.value?.data, successResponse.data)
        }
    }

    @Test
    fun `test retrieveEmployeeList should make service call if the live data value is null`() {
        runTest {
            coEvery { employeeService.getEmployees() } returns successResponse
            sut.retrieveEmployeeList(false)
            coVerify(exactly = 1) { employeeService.getEmployees()  }
            Assert.assertEquals(sut.employeeListLiveData.value?.status, Resource.Status.SUCCESS)
            Assert.assertEquals(sut.employeeListLiveData.value?.data, successResponse.data)
        }
    }

    @Test
    fun `test retrieveEmployeeList should not make service call if the search results are available`() {
        runTest {
            coEvery { employeeService.getEmployees() } returns successResponse
            sut.retrieveEmployeeList()
            Assert.assertEquals(sut.employeeListLiveData.value?.status, Resource.Status.SUCCESS)
            Assert.assertEquals(sut.employeeListLiveData.value?.data, successResponse.data)
            sut.retrieveEmployeeList()
            coVerify(exactly = 1) { employeeService.getEmployees() }
        }
    }

    @Test
    fun `test retrieveEmployeeList when service call fails`() {
        runTest {
            coEvery { employeeService.getEmployees() } returns errorResponse
            sut.retrieveEmployeeList()
            Assert.assertEquals(sut.employeeListLiveData.value?.status, Resource.Status.ERROR)
            Assert.assertEquals(sut.employeeListLiveData.value?.errorCode, errorResponse.errorCode)
            Assert.assertEquals(sut.employeeListLiveData.value?.errorMsg, errorResponse.errorMsg)
            coVerify(exactly = 1) { employeeService.getEmployees() }
        }
    }

    @Test
    fun `test getSortedList sorts correct Asc Order`() {
        val e2 = e1.copy(fullName = "Ossie")
        val e3 = e1.copy(fullName = "Naz")
        val e4 = e1.copy(fullName = "Zayn")

        val list = listOf(
            e1, e2, e3, e4
        )
        val expectedList = listOf(e1, e3, e2, e4)
        val sortedList = sut.getSortedList(list, MainViewModel.SORT_ORDER_NAME_ASC)
        Assert.assertEquals(sortedList, expectedList)
    }

    @Test
    fun `test getSortedList sorts correct DESC Order`() {
        val e2 = e1.copy(fullName = "Ossie")
        val e3 = e1.copy(fullName = "Naz")
        val e4 = e1.copy(fullName = "Zayn")
        val list = listOf(
            e1, e2, e3, e4
        )
        val expectedList = listOf(e4, e2, e3, e1)
        val sortedList = sut.getSortedList(list, MainViewModel.SORT_ORDER_NAME_DESC)
        Assert.assertEquals(sortedList, expectedList)
    }
}