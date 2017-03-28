import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class CategoryTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteTasksQuery = "DELETE FROM tasks *;";
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      con.createQuery(deleteTasksQuery).executeUpdate();
      con.createQuery(deleteCategoriesQuery).executeUpdate();
    }
  }

  // @Test
  // public void category_instantiatesCorrectly_true(){
  //   Category testCategory = new Category("Home");
  //   assertEquals(true, testCategory instanceof Category);
  // }
  // @Test
  // public void getName_categoryInstantiatesWithName_Home(){
  //   Category testCategory = new Category("Home");
  //   assertEquals("Home", testCategory.getName());
  // }
  // @Test
  // public void all_returnsAllInstancesOfCategory_true(){
  //   Category firstCategory = new Category("Home");
  //   Category secondCategory = new Category("Work");
  //   assertEquals(true, Category.all().contains(firstCategory));
  //   assertEquals(true, Category.all().contains(secondCategory));
  // }
  //
    @Test
    public void getId_categoriesInstantiateWithAnId_1() {
      Category testCategory = new Category("Home");
      testCategory.saveCategory();
      assertTrue(testCategory.getId() > 0);
    }
  // @Test
  // public void find_returnsCategoryWithSameId_secondCategory(){
  //   Category firstCategory = new Category("Home");
  //   Category secondCategory = new Category("Work");
  //   assertEquals(Category.find(secondCategory.getId()), secondCategory);
  // }
  // @Test
  // public void getTasks_initiallyReturnsEmptyList_ArrayList(){
  //   Category testCategory = new Category("Home");
  //   assertEquals(0, testCategory.getTasks().size());
  // }
  // @Test
  // public void addTask_addsTaskToList_true(){
  //   Category testCategory = new Category("Home");
  //   Task testTask = new Task("Mow the lawn");
  //   testCategory.addTask(testTask);
  //   assertTrue(testCategory.getTasks().contains(testTask));
  // }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Category firstCategory = new Category("Household chores");
    Category secondCategory = new Category("Household chores");
    assertTrue(firstCategory.equals(secondCategory));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Category myCategory = new Category("Household chores");
    myCategory.saveCategory();
    assertTrue(Category.all().get(0).equals(myCategory));
  }

  @Test
  public void all_returnsAllInstancesOfCategory_true() {
    Category firstCategory = new Category("Home");
    firstCategory.saveCategory();
    Category secondCategory = new Category("Work");
    secondCategory.saveCategory();
    assertEquals(true, Category.all().get(0).equals(firstCategory));
    assertEquals(true, Category.all().get(1).equals(secondCategory));
  }

  @Test
  public void save_assignsIdToObject() {
    Category myCategory = new Category("Household chores");
    myCategory.saveCategory();
    Category savedCategory = Category.all().get(0);
    assertEquals(myCategory.getId(), savedCategory.getId());
  }

  @Test
  public void find_returnsCategoryWithSameId_secondCategory() {
    Category firstCategory = new Category("Home");
    firstCategory.saveCategory();
    Category secondCategory = new Category("Work");
    secondCategory.saveCategory();
    assertEquals(Category.find(secondCategory.getId()), secondCategory);
  }

  @Test
  public void save_savesCategoryIdIntoDB_true() {
    Category myCategory = new Category("Household chores");
    myCategory.saveCategory();
    Task myTask = new Task("Mow the lawn", myCategory.getId());
    myTask.saveTask();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(savedTask.getCategoryId(), myCategory.getId());
  }

  @Test
  public void update_updatesCategoryName_true() {
    Category myCategory = new Category("Home");
    myCategory.saveCategory();
    myCategory.update("Work");
    assertEquals("Work", Category.find(myCategory.getId()).getName());
  }
}
