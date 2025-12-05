package com.liaw.dev.Conference.pix;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PixService {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("{CLIENT_SECRET}")
    private String clientSecret;

    @Value("{PIXKEY}")
    private String pixKey;

    public JSONObject createPixCharge(String value, String name, String cpf){
        JSONObject configuration = configuration();
        JSONObject body = new JSONObject();

        body.put("calendario", new JSONObject().put("expiracao", 3600));
        body.put("devedor", new JSONObject().put("nome", name).put("cpf", cpf));
        body.put("valor", new JSONObject().put("original", value));
        body.put("chave", "lucasliaw50@gmail.com");
        body.put("solicitacaoPagador", "Pagamento da conferência");

        try {
            EfiPay efiPay = new EfiPay(configuration);
            JSONObject response = efiPay.call("pixCreateImmediateCharge", new HashMap<String, String>(), body);
            System.out.println(response.toString());
            return response;
        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String checkPaymentStatus(String txid){
        JSONObject options = configuration();
        HashMap<String, String> params = new HashMap<>();
        params.put("txid", txid);

        try {
            EfiPay efiPay = new EfiPay(options);
            JSONObject response = efiPay.call("pixDetailCharge", params, new JSONObject());
            if (response.has("pix")){
                JSONArray pixArray = response.getJSONArray("pix");

                if (pixArray.length() > 0){
                    return "CONCLUIDA";
                }
            }
        }
        catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "PENDENTE";
    }

    public JSONObject configuration(){
        Credentials credentials = new Credentials();
        JSONObject options = new JSONObject();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.getSandbox());
        return options;
    }

}
