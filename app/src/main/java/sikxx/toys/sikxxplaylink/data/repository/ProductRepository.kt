package sikxx.toys.sikxxplaylink.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import sikxx.toys.sikxxplaylink.data.model.Product
import sikxx.toys.sikxxplaylink.data.model.ProductCategory

class ProductRepository {
    private val products = listOf(
        product(1, "Woodland Memory Match", "Turn over illustrated cards and find every pair.", ProductCategory.BOARD_GAMES, 18.90, "1610890716171-6b1bb98ffd09"),
        product(2, "Rainbow Builder Blocks", "Build towers and bridges with smooth wooden arches.", ProductCategory.BUILDING_SETS, 32.50, "1598880940080-ff9a29891b85"),
        product(3, "Cosy Cloud Bear", "An extra-soft friend for stories and bedtime routines.", ProductCategory.PLUSH_TOYS, 21.00, "1559454403-b8fb88521f11"),
        product(4, "Code & Roll Robot", "Create movement sequences and watch the robot follow.", ProductCategory.ROBOTS, 44.95, "1560961911-ba7ef651a56c"),
        product(5, "Family Story Cubes", "Roll picture cubes and invent a new tale together.", ProductCategory.BOARD_GAMES, 14.75, "1606503153255-59d8b8b82176"),
        product(6, "Magnetic City Tiles", "Create houses and vehicles with magnetic tiles.", ProductCategory.BUILDING_SETS, 39.90, "1594787318286-3d835c1d207f"),
        product(7, "Pocket Dino Friend", "A squishy dinosaur for travel and make-believe.", ProductCategory.PLUSH_TOYS, 16.40, "1563901935883-cb61f5d49be4"),
        product(8, "Explorer Rover Kit", "Build a rover, explore gears, and guide it around obstacles.", ProductCategory.ROBOTS, 52.00, "1546776310-eef45dd6d63c"),
        product(9, "Little Artist Studio", "A reusable case filled with colourful art essentials.", ProductCategory.CREATIVE_PLAY, 27.60, "1596464716127-f2a82984de30"),
        product(10, "Ocean Puzzle Journey", "Discover sea creatures in a 100-piece puzzle.", ProductCategory.BOARD_GAMES, 19.50, "1587654780291-39c9404d746b"),
        product(11, "Junior Marble Run", "Plan winding tracks and explore speed and gravity.", ProductCategory.BUILDING_SETS, 36.80, "1594736797933-d0501ba2fe65"),
        product(12, "Make-a-Monster Craft Box", "Create a friendly monster from colourful craft pieces.", ProductCategory.CREATIVE_PLAY, 23.25, "1618842676088-c4d48a6a7c9d")
    )

    fun observeById(id: Int): Flow<Product?> = flowOf(getById(id))

    fun getById(id: Int): Product? = products.find { it.id == id }

    fun observeAll(): Flow<List<Product>> = flowOf(products)

    private fun product(
        id: Int,
        title: String,
        description: String,
        category: ProductCategory,
        price: Double,
        photoId: String
    ): Product {
        return Product(
            id = id,
            title = title,
            description = description,
            category = category,
            price = price,
            imageUrl = "https://images.unsplash.com/photo-$photoId?w=1200"
        )
    }
}
