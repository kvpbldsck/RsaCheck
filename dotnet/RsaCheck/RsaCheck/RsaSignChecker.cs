using System.Security.Cryptography;
using System.Text;

namespace RsaCheck;

public sealed class RsaSignChecker
{
    private readonly ServerRequestSignatureHolder _signatureHolder;
    private readonly string _publicKey;

    public RsaSignChecker(ServerRequestSignatureHolder signatureHolder, string publicKey)
    {
        _signatureHolder = signatureHolder;
        _publicKey = publicKey;
    }

    public bool Validate(string message)
    {
        byte[] bytesToVerify = Encoding.UTF8.GetBytes(message);
        byte[] signatureBytes = Convert.FromBase64String(_signatureHolder.Sign);

        using var rsa = RSA.Create();
        rsa.ImportFromPem(_publicKey);

        return rsa.VerifyData(bytesToVerify, signatureBytes, HashAlgorithmName.SHA256, RSASignaturePadding.Pkcs1);
    }
}
