import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'

// HEAD LIBRARIAN PAGES
import CreateShift from '@/components/HeadLibrarian/CreateShift.vue'
import CreateLibrarian from '@/components/HeadLibrarian/CreateLibrarian.vue'
import UpdateLibrary from '@/components/HeadLibrarian/UpdateLibrary.vue'
import CreateOpeningHour from '@/components/HeadLibrarian/CreateOpeningHour.vue'
import ShiftPage from '@/components/HeadLibrarian/ShiftPage.vue'
import UpdateOpeningHours from '@/components/HeadLibrarian/UpdateOpeningHours.vue'
import OpeningHoursHead from '@/components/HeadLibrarian/OpeningHoursHead.vue'
import LoginHeadLibrarian from '@/components/HeadLibrarian/LoginHeadLibrarian.vue'
import HeadLibrarianMainPage from '@/components/HeadLibrarian/HeadLibrarianMainPage.vue'
import ListOfLibrarians from '@/components/HeadLibrarian/ListOfLibrarians.vue'

// CUSTOMER PAGES
import LoginCustomer from '@/components/Customer/LoginCustomer.vue'
import SignUpCustomer from '@/components/Customer/SignUpCustomer.vue'
import CreateLoginCredential from '@/components/Customer/CreateLoginCredential.vue'
import CustomerMainPage from '@/components/Customer/CustomerMainPage.vue'
import CustomerItems from '@/components/Customer/CustomerItems.vue'
import Payment from '@/components/Customer/Payment.vue'
import CustomerEvents from '@/components/Customer/CustomerEvents.vue'
import OpeningHoursCustomer from '@/components/Customer/OpeningHoursCustomer.vue'
import LibraryCustomer from '@/components/Customer/LibraryCustomer.vue'
import CustomerCatalogue from '@/components/Customer/CustomerCatalogue.vue'
import CustomerUpdateAccount from '@/components/Customer/CustomerUpdateAccount.vue'
import CustomerUpdatePersonalInfo from '@/components/Customer/CustomerUpdatePersonalInfo.vue'


// LIBRARIAN PAGES
import LoginLibrarian from '@/components/Librarian/LoginLibrarian.vue'
import LibrarianMainPage from '@/components/Librarian/LibrarianMainPage.vue'
import ListOfEvents from '@/components/Librarian/ListOfEvents.vue'
import ListOfCustomers from '@/components/Librarian/ListOfCustomers.vue'
import LibrarianCreateCustomer from '@/components/Librarian/LibrarianCreateCustomer.vue'
import LibrarianUpdateCustomer from '@/components/Librarian/LibrarianUpdateCustomer.vue'
import LibrarianCreateLoginCredential from '@/components/Librarian/LibrarianCreateLoginCredential.vue'
import UpdateLibrarianAccount from '@/components/Librarian/UpdateLibrarianAccount.vue'
import MyShifts from '@/components/Librarian/MyShifts.vue'
import LibrarianCreateItem from '@/components/Librarian/LibrarianCreateItem.vue'
import LibrarianUpdateItem from '@/components/Librarian/LibrarianUpdateItem.vue'
import LibrarianCatalogue from '@/components/Librarian/LibrarianCatalogue.vue'
import listOfBookings from '@/components/Librarian/listOfBookings.vue'
import LibraryInfoLibrarian from '@/components/Librarian/LibraryInfoLibrarian.vue'
import OpeningHoursLibrarian from '@/components/Librarian/OpeningHoursLibrarian.vue'
import LibrarianCheckoutItem from '@/components/Librarian/LibrarianCheckoutItem.vue'


// Event

import CreateEvent from '@/components/Customer/CreateEvent.vue'
import CreateEventLibrarian from '@/components/Librarian/CreateEventLibrarian.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/CreateShift',
      name: 'CreateShift',
      component: CreateShift
    },
    {
      path: '/CreateOpeningHour',
      name: 'CreateOpeningHour',
      component: CreateOpeningHour
    },
    {
      path: '/LibrarianCreateCustomer',
      name: 'LibrarianCreateCustomer',
      component: LibrarianCreateCustomer
    },
    {
      path: '/LibrarianCheckoutItem',
      name: 'LibrarianCheckoutItem',
      component: LibrarianCheckoutItem
    },
    {
      path: '/LibrarianCreateLoginCredential',
      name: 'LibrarianCreateLoginCredential',
      component: LibrarianCreateLoginCredential
    },
    {
      path: '/LibraryInfoLibrarian',
      name: 'LibraryInfoLibrarian',
      component: LibraryInfoLibrarian
    },
    {
      path: '/LibrarianUpdateCustomer',
      name: 'LibrarianUpdateCustomer',
      component: LibrarianUpdateCustomer
    },
    {
      path: '/ShiftPage',
      name: 'ShiftPage',
      component: ShiftPage
    },
    {
      path: '/CreateEventLibrarian',
      name: 'CreateEventLibrarian',
      component: CreateEventLibrarian
    },
    {
      path: '/OpeningHoursLibrarian',
      name: 'OpeningHoursLibrarian',
      component: OpeningHoursLibrarian
    },
    {
      path: '/CreateEvent',
      name: 'CreateEvent',
      component: CreateEvent
    },
    {
      path: '/LibraryCustomer',
      name: 'LibraryCustomer',
      component: LibraryCustomer
    },
    {
    path: '/OpeningHoursCustomer',
    name: 'OpeningHoursCustomer',
    component: OpeningHoursCustomer
    },
    {
      path: '/OpeningHoursHead',
      name: 'OpeningHours',
      component: OpeningHoursHead
    },
    {
      path: '/UpdateOpeningHours',
      name: 'UpdateOpeningHours',
      component: UpdateOpeningHours
    },
    {
      path: '/CreateLibrarian',
      name: 'CreateLibrarian',
      component: CreateLibrarian
    },
    {
      path: '/UpdateLibrary',
      name: 'UpdateLibrary',
      component: UpdateLibrary
    },
    {
      path: '/SignUpCustomer',
      name: 'SignUpCustomer',
      component: SignUpCustomer
    },
    {
      path: '/CreateLoginCredential',
      name: 'CreateLoginCredential',
      component: CreateLoginCredential
    },
    {
      path: '/CustomerMainPage',
      name: 'CustomerMainPage',
      component: CustomerMainPage
    },
    {
      path: '/CustomerEvents',
      name: 'CustomerEvents',
      component: CustomerEvents
    },
    {
      path: '/Payment',
      name: 'Payment',
      component: Payment
    },
    {
      path: '/CustomerItems',
      name: 'CustomerItems',
      component: CustomerItems
    },
    {
      path: '/LoginLibrarian',
      name: 'LoginLibrarian',
      component: LoginLibrarian
    },
    {
      path: '/UpdateLibrarianAccount',
      name: 'UpdateLibrarianAccount',
      component: UpdateLibrarianAccount
    },
    {
      path: '/LoginHeadLibrarian',
      name: 'LoginHeadLibrarian',
      component: LoginHeadLibrarian
    },
    {
      path: '/LibrarianMainPage',
      name: 'LibrarianMainPage',
      component: LibrarianMainPage
    },
    {
      path: '/MyShifts',
      name: 'MyShifts',
      component: MyShifts
    },
    {
      path: '/HeadLibrarianMainPage',
      name: 'HeadLibrarianMainPage',
      component: HeadLibrarianMainPage
    },
    {
      path: '/ListOfEvents',
      name: 'ListOfEvents',
      component: ListOfEvents
    },
    {
      path: '/ListOfCustomers',
      name: 'ListOfCustomers',
      component: ListOfCustomers
    },
    {
      path: '/ListOfLibrarians',
      name: 'ListOfLibrarians',
      component: ListOfLibrarians
    },
    {
      path: '/LoginCustomer',
      name: 'LoginCustomer',
      component: LoginCustomer
    },
    {
      path: '/CustomerCatalogue',
      name: 'CustomerCatalogue',
      component: CustomerCatalogue
    },
    {
      path: '/LibrarianCreateItem',
      name: 'LibrarianCreateItem',
      component: LibrarianCreateItem
    },
    {
      path: '/LibrarianCatalogue',
      name: 'LibrarianCatalogue',
      component: LibrarianCatalogue
    },
    {
      path: '/LibrarianUpdateItem',
      name: 'LibrarianUpdateItem',
      component: LibrarianUpdateItem
    },
    {
      path: '/listOfBookings',
      name: 'listOfBookings',
      component: listOfBookings
    },
    {
      path: '/CustomerUpdateAccount',
      name: 'CustomerUpdateAccount',
      component: CustomerUpdateAccount
    },
    {
      path: '/CustomerUpdatePersonalInfo',
      name: 'CustomerUpdatePersonalInfo',
      component: CustomerUpdatePersonalInfo
    }
  ]
})
