namespace RsaCheck;

public static class Program
{
    public static void Main()
    {
        var message = ReadResource("message.json");
        var privateKey = ReadResource("privateKey");
        var publicKey = ReadResource("publicKey");
        
        var signer = new RsaSigner(privateKey);
        
        string sign = signer.MakeBase64Signature(message);

        Console.WriteLine(sign);
        
        var checker = new RsaSignChecker(
            new ServerRequestSignatureHolder(sign),
            publicKey);

        bool isValid = checker.Validate(message);

        Console.WriteLine($"is valid: {isValid}");
    }

    private static string ReadResource(string path) 
        => Resources.Resources.ResourceManager.GetString(path)
           ?? throw new InvalidOperationException();
}
