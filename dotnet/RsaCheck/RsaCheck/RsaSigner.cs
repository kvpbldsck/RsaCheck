using System.Security.Cryptography;
using System.Text;

namespace RsaCheck;

public sealed class RsaSigner
{
    private readonly string _privateKey;

    public RsaSigner(string privateKey)
    {
        _privateKey = privateKey;
    }

    public string MakeBase64Signature(string message)
    {
        byte[] bytesToSign = Encoding.UTF8.GetBytes(message);

        using var rsa = RSA.Create();
        rsa.ImportFromPem(_privateKey);

        byte[] signature = rsa.SignData(bytesToSign, HashAlgorithmName.SHA256, RSASignaturePadding.Pkcs1);

        return Convert.ToBase64String(signature);
    }
}
