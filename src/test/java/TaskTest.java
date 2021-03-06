import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.sql2o.*;

public class TaskTest {

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
  // public void Task_instantiatesCorrectly_true(){
  //   Task myTask = new Task("Mow the lawn");
  //   assertEquals(true, myTask instanceof Task);
  // }
  // @Test
  // public void Task_instantiatesWithDescription_String(){
  //   Task myTask = new Task("Mow the lawn");
  //   assertEquals("Mow the lawn", myTask.getDescription());
  // }
  // @Test
  // public void isCompleted_isFalseAfterInstantiation_false() {
  //   Task myTask = new Task("Mow the lawn");
  //   assertEquals(false, myTask.isCompleted());
  // }
  // @Test
  // public void getCreatedAt_instantiatesWithCurrentTime_today() {
  //   Task myTask = new Task("Mow the lawn");
  //   assertEquals(LocalDateTime.now().getDayOfWeek(), myTask.getCreatedAt().getDayOfWeek());
  // }
  // @Test
  // public void all_returnsAllInstancesOfTask_true() {
  //   Task firstTask = new Task("Mow the lawn");
  //   Task secondTask = new Task("Buy groceries");
  //   assertEquals(true, Task.all().contains(firstTask));
  //   assertEquals(true, Task.all().contains(secondTask));
  // }
  // @Test
  // public void clear_emptiesAllTasksFromArrayList_0() {
  //   Task myTask = new Task("Mow the lawn");
  //   Task.clear();
  //   assertEquals(0, Task.all().size());
  // }
  //
  @Test
  public void getId_tasksInstantiatesWithAnID() {
    Task myTask = new Task("Mow the lawn", 1);
    myTask.saveTask();
    assertTrue(myTask.getId() > 0);
  }

  @Test
  public void find_returnsTaskWithSameId_secondTask() {
    Task firstTask = new Task("Mow the Lawn", 1);
    firstTask.saveTask();
    Task secondTask = new Task("Buy groceries", 2);
    secondTask.saveTask();
    assertEquals(Task.find(secondTask.getId()), secondTask);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame() {
    Task firstTask = new Task("Mow the lawn", 1);
    Task secondTask = new Task("Mow the lawn", 1);
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAreTheSame() {
    Task myTask = new Task("Mow the lawn", 1);
    myTask.saveTask();
    assertTrue(Task.all().get(0).equals(myTask));
  }

  @Test
  public void all_returnsAllInstancesOfTask_true() {
    Task firstTask = new Task("Mow the lawn", 1);
    firstTask.saveTask();
    Task secondTask = new Task("Buy groceries", 2);
    secondTask.saveTask();
    assertEquals(true, Task.all().get(0).equals(firstTask));
    assertEquals(true, Task.all().get(1).equals(secondTask));
  }

  @Test
  public void save_assignsIdToObject() {
    Task myTask = new Task("Mow the lawn", 1);
    myTask.saveTask();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void update_updatesTaskDescription_true() {
    Task myTask = new Task("Mow the lawn", 1);
    myTask.saveTask();
    myTask.updateTask("Take a nap");
    assertEquals("Take a nap", Task.find(myTask.getId()).getDescription());
  }

}
