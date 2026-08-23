package sikxx.toys.sikxxplaylink.di

import sikxx.toys.sikxxplaylink.ui.viewmodel.AppViewModel
import sikxx.toys.sikxxplaylink.ui.viewmodel.CartViewModel
import sikxx.toys.sikxxplaylink.ui.viewmodel.CheckoutViewModel
import sikxx.toys.sikxxplaylink.ui.viewmodel.TNQRSOnboardingVM
import sikxx.toys.sikxxplaylink.ui.viewmodel.OrderViewModel
import sikxx.toys.sikxxplaylink.ui.viewmodel.ProductDetailsViewModel
import sikxx.toys.sikxxplaylink.ui.viewmodel.ProductViewModel
import sikxx.toys.sikxxplaylink.ui.viewmodel.TNQRSSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        TNQRSSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        TNQRSOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}