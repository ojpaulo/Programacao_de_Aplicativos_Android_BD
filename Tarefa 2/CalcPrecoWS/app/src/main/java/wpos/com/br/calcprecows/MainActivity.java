package wpos.com.br.calcprecows;

import java.io.IOException;
// Todas as bibliotecas necessárias do KSOAP2
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;



public class MainActivity extends AppCompatActivity {
    //EditText txtCepOrigem;
    //EditText txtCepDestino;
    EditText txtPeso;
    Spinner spiTipoServico;
    TextView txtValor;
    TextView txtMensagem;


    LinearLayout linhaValor;
    LinearLayout linhaMensagem;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Em API maiores que 9, é necessário habilitar um ThreadPolicy
// para liberar o acesso ao WebService externo
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
// Identifica os elementos de layout para manipulação





        //txtCepOrigem = (EditText) findViewById(R.id.txtCepOrigem);
        //txtCepDestino = (EditText) findViewById(R.id.txtCepDestino);

        txtPeso = (EditText) findViewById(R.id.txtPeso);
        spiTipoServico = (Spinner) findViewById(R.id.spiTipoServico);
        txtValor = (TextView) findViewById(R.id.txtValor);
        txtMensagem = (TextView) findViewById(R.id.txtMensagem);

        linhaValor = (LinearLayout) findViewById(R.id.linhaValor);
        linhaMensagem = (LinearLayout) findViewById(R.id.linhaMensagem);








    }
    public void calcularValor(View v)
    {


        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);


// Informações de Conexão WebServices
        final String NAMESPACE = "http://tempuri.org/";
        final String METHOD_NAME = "CalcPrecoFAC";
        final String SOAP_ACTION = "http://tempuri.org/CalcPrecoFAC";
        final String URL ="http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx?wsdl";


// Montando a Requisição SOAP com as informações a serem enviadas
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
// Transforma texto de serviço em código de serviço
        String nCdServico = "";
        switch(spiTipoServico.getSelectedItem().toString())
        {


            case "FAC SIMPLES LOCAL":
            nCdServico = "82015";
            break;
            case "FAC SIMPLES ESTADUAL":
            nCdServico = "82023";
            break;
            case "FAC SIMPLES NACIONAL":
            nCdServico = "82031";
            break;
            case "FAC REGISTRADO LOCAL":
            nCdServico = "82104";
            break;
            case "FAC REGISTRADO ESTADUAL":
            nCdServico = "82112";
            break;
            case "FAC REGISTRADO NACIONAL":
            nCdServico = "82120";
            break;
            case "FAC REGISTRADO LOCAL COM AR":
            nCdServico = "82139";
            break;
            case "FAC REGISTRADO ESTADUAL COM AR":
            nCdServico = "82147";
            break;
            case "FAC REGISTRADO NACIONAL COM AR":
            nCdServico = "82155";
            break;
            case "FAC SIMPLES LOCAL ACIMA 500 G":
            nCdServico = "82309";
            break;
            case "FAC SIMPLES ESTAD ACIMA 500 G":
            nCdServico = "82317";
            break;
            case "FAC SIMPLES NAC ACIMA 500 G":
            nCdServico = "82325";
            break;
            case "FAC REGIST LOCAL ACIMA 500 G":
            nCdServico = "82333";
            break;
            case "FAC REGISTRADO EST ACIMA 500 G":
            nCdServico = "82341";
            break;
            case "FAC REGISTRADO NAC ACIMA 500 G":
            nCdServico = "82350";
            break;
           case "FAC REG LOCAL C/ AR ACIMA 500G":
            nCdServico = "82368";
            break;
            case "FAC REG EST C/ AR ACIMA 500 G":
            nCdServico = "82376";
            break;
            case "FAC REG NAC C/ AR ACIMA 500 G":
            nCdServico = "82384";
            break;

        }
// Adiciona informações à requisição


        request.addProperty("nCdServico", nCdServico);
        request.addProperty("nVlPeso", txtPeso.getText().toString());
        request.addProperty("strDataCalculo", dataFormatada );





// Criando o Envelope de Envio
        SoapSerializationEnvelope envelope = new
                SoapSerializationEnvelope(SoapEnvelope.VER11);
// Por ser um .ASMX, o Servidor de WebService é feito no padrão .NET. Isso
// deve ser informado ao KSOAP por se tratar de um padrão diferente.
        envelope.dotNet = true;
// Adicionando a Requisição SOAP ao seu envelope
        envelope.setOutputSoapObject(request);
// Colocar o envelope SOAP em um protocolo HTTP de transporte
        HttpTransportSE transport = new HttpTransportSE(URL);
        try {
// Realiza a chamada, enviando as informações
            transport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.bodyIn;

// Descendo os níveis do XML devolvido pelo SOAP
            SoapObject responseAnyType = (SoapObject) response.getProperty(0);
            SoapObject responseServicos = (SoapObject) responseAnyType.getProperty("Servicos");
            SoapObject responseCServico = (SoapObject) responseServicos.getProperty("cServico");
// Exibe o prazo na tela.
           //txvPrazo.setText(responseCServico.getProperty("PrazoEntrega").toString()+" dias");
// Verifica o "S" e "N" retornado e exibe as palavras em tela.
            //if
            //        (responseCServico.getProperty("EntregaSabado").toString().equals("S"))
           // {
           //     txvEntregaSabado.setText("Sim");
           // }
           // else
           //{
           //    txvEntregaSabado.setText("Não");
           //}

            txtValor.setText(responseCServico.getProperty("Valor").toString());
            ///txtMensagem.setText(responseCServico.getProperty("Erro").toString());
            txtMensagem.setText(responseCServico.getProperty("MsgErro").toString());
            //txtPrazoEntrega.setText(responseCServico.getProperty("PrazoEntrega").toString());
            //txtValorMaoPropria.setText(responseCServico.getProperty("ValorMaoPropria").toString());
            //txtValorAvisoRecebimento.setText(responseCServico.getProperty("ValorAvisoRecebimento").toString());
            //txtValorDeclarado.setText(responseCServico.getProperty("ValorDeclarado").toString());
            //txtEntregaDomiciliar.setText(responseCServico.getProperty("EntregaDomiciliar").toString());
            //txtValorSemAdicionais.setText(responseCServico.getProperty("ValorSemAdicionais").toString());
            //txtobsFim.setText(responseCServico.getProperty("obsFim").toString());
            //txtDataMaxEntrega.setText(responseCServico.getProperty("DataMaxEntrega").toString());
            //txtHoraMaxEntrega.setText(responseCServico.getProperty("HoraMaxEntrega").toString());









// Torna as linhas de resultado visíveis
            //linhaPrazo.setVisibility(1);


            linhaValor.setVisibility(1);
            linhaMensagem.setVisibility(1);
            linhaValor.setVisibility(1);
            linhaMensagem.setVisibility(1);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
