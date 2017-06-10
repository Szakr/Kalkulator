package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Controller {

    private String operator;
    private String number = "",number2 = "";
    private boolean result = false;
    private int base = 10;

    @FXML
    ComboBox numeralSystem;

    @FXML
    Button btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;


    @FXML
    TextField display;

    @FXML
    void initialize(){
        ObservableList<String> options =
                FXCollections.observableArrayList("Dziesiętny", "Dziewiątkowy", "Ósemkowy", "Siódemkowy",
                        "Szóstkowy", "Piątkowy", "Czwórkowy", "Trójkowy", "Binarny");
        numeralSystem.getItems().addAll(options);
        numeralSystem.setValue("Dziesiętny");
        numeralSystem.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                clearAll();
                block();
                getBase((String) newValue);
                unblock(base);
            }
        });
    }

    @FXML
    void digitClicked(Event event){
        Button btn = (Button) event.getSource();
        if (result){
            display.clear();
            result=false;
        }
        display.setText(display.getText() + btn.getText());
    }

    @FXML
    void operatorClicked(Event event){
        Button btn = (Button) event.getSource();
        if (!result) {
            if (number2.isEmpty() && !number.isEmpty()) {
                number2 = display.getText();
                if (number2.isEmpty()){
                    operator = btn.getText();
                    return;
                }
                if (operator.equals("/") && number2.equals("0")) {
                    clearAll();
                }else{
                    number = Integer.toString(calculate(),base);
                    display.setText(number);
                    number2 = "";
                    result = true;
                }
            }
            if (number.isEmpty()) {
                number = display.getText();
                display.clear();

            }
        } else if (!display.getText().equals("Error")) number = display.getText();
        operator = btn.getText();
    }

    @FXML
    void equalClicked(){
        if (number2.isEmpty() && !number.isEmpty() && !result && !operator.isEmpty()){
            number2 = display.getText();
            if (number2.isEmpty()) return;
            if (operator.equals("/") && number2.equals("0")) {
                clearAll();
                display.setText("Error");
                result = true;
            }else{
                number = Integer.toString(calculate(),base);
                display.setText(number);
                result = true;
                operator = "";
                number2 = "";
                number = "";
            }
        }
    }

    @FXML
    void clearAll(){
        display.clear();
        number = "";
        number2 = "";
        result = false;
    }

    private int calculate(){
        int num = Integer.parseInt(number, base);
        int num2 = Integer.parseInt(number2, base);
        switch (operator){
            case "+":
                num+=num2;
                break;
            case "-":
                num-=num2;
                break;
            case "*":
                num*=num2;
                break;
            case "/":
                num/=num2;
                break;
        }
        return num;
    }

    private void block(){
        btn2.setDisable(true);
        btn3.setDisable(true);
        btn4.setDisable(true);
        btn5.setDisable(true);
        btn6.setDisable(true);
        btn7.setDisable(true);
        btn8.setDisable(true);
        btn9.setDisable(true);
    }

    private void unblock(int b){
        switch (b){
            case 10:
                btn9.setDisable(false);
            case 9:
                btn8.setDisable(false);
            case 8:
                btn7.setDisable(false);
            case 7:
                btn6.setDisable(false);
            case 6:
                btn5.setDisable(false);
            case 5:
                btn4.setDisable(false);
            case 4:
                btn3.setDisable(false);
            case 3:
                btn2.setDisable(false);
            case 2:
                break;
        }
    }

    private void getBase(String choice){
        switch (choice){
            case "Dziesiętny": base = 10;
            break;
            case "Dziewiątkowy": base = 9;
                break;
            case "Ósemkowy": base = 8;
                break;
            case "Siódemkowy": base = 7;
                break;
            case "Szóstkowy": base = 6;
                break;
            case "Piątkowy": base = 5;
                break;
            case "Czwórkowy": base = 4;
                break;
            case "Trójkowy": base = 3;
                break;
            case "Binarny": base = 2;
                break;
        }
    }
}
