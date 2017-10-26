import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CadastroUsuariosAutomatedTest {


    WebDriver driver;
    ChromeOptions options;

    @BeforeClass
    public static void beforeClass(){
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
    }

    @Before
    public void setUp(){
        options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);
        //driver = new ChromeDriver();
        //driver.manage().window().maximize();
    }

    @Test
    public void deveCadastrarUsuario(){
        driver.get("http://localhost:8080/usuarios/new");
        WebElement nome = driver.findElement(By.name("usuario.nome"));
        WebElement email = driver.findElement(By.name("usuario.email"));

        String nomeJoao = "Joao da Silva Pereira";
        String emailJoao = "joaosilvapereira@gmail.com";
        nome.sendKeys(nomeJoao);
        email.sendKeys(emailJoao);

        driver.findElement(By.id("btnSalvar")).click();

        boolean achouNome = driver.getPageSource().contains(nomeJoao);
        boolean achouEmail = driver.getPageSource().contains(emailJoao);

        assertTrue(achouNome);
        assertTrue(achouEmail);
    }

    @Test
    public void deveRetornarErroCadastroSemNome(){
        driver.get("http://localhost:8080/usuarios/new");
        WebElement nome = driver.findElement(By.name("usuario.nome"));
        WebElement email = driver.findElement(By.name("usuario.email"));

        String nomeJoao = "";
        String emailJoao = "joaosilvapereira@gmail.com";
        nome.sendKeys(nomeJoao);
        email.sendKeys(emailJoao);

        driver.findElement(By.id("btnSalvar")).click();

        boolean erro = driver.getPageSource().contains("Nome obrigatorio!");
        assertTrue(erro);
    }

    @Test
    public void deveRetornarErroCadastroSemNomeSemEmail(){
        driver.get("http://localhost:8080/usuarios/new");
        driver.findElement(By.id("btnSalvar")).click();

        boolean erroNome = driver.getPageSource().contains("Nome obrigatorio!");
        assertTrue(erroNome);

        boolean erroEmail = driver.getPageSource().contains("E-mail obrigatorio!");
        assertTrue(erroEmail);
    }

    @Test
    public void deveVerificarSeOLinkNovoUsuarioFunciona(){
        driver.get("http://localhost:8080/usuarios");
        WebElement linkNovoUsuario = driver.findElement(By.linkText("Novo Usuário"));
        linkNovoUsuario.click();

        assertEquals("http://localhost:8080/usuarios/new", driver.getCurrentUrl());
    }

    @After
    public void close(){
        driver.close();
    }
}
