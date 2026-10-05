# Captura os dados básicos do ambiente
$usuario = $env:USERNAME
$maquina = $env:COMPUTERNAME
$setorAd = "Setor Não Identificado"

try {
    # TENTATIVA 1: Busca o caminho da máquina no AD de forma mais furtiva e compatível
    $searcher = [adsisearcher]"(sAMAccountName=$maquina`$)"
    $resultado = $searcher.FindOne()
    
    if ($null -ne $resultado) {
        $caminhoDn = $resultado.Properties["distinguishedname"][0]
        
        # Extrai o nome da OU (Unidade Organizacional)
        if ($caminhoDn -match "OU=([^,]+)") {
            $setorAd = $matches[1]
        }
    } else {
        # Força o erro para cair no Plano B se o AD não retornar nada
        throw "Máquina não encontrada na pesquisa." 
    }
} catch {
    # TENTATIVA 2 (PLANO B): Fallback inteligente
    # Se o AD bloquear a leitura, o script corta o nome da máquina (ex: GETIN-D19 vira GETIN)
    if ($maquina -match "^([A-Za-z0-9]+)-") {
        $setorAd = $matches[1]
    }
}

$apiUrl = "http://GETIN-D19:8080/api/alertas"

# Monta o pacote de dados
$body = @{
    usuarioAd = $usuario
    nomeMaquina = $maquina
    setor = $setorAd
} | ConvertTo-Json

# Envia para a API Spring Boot
try {
    Invoke-RestMethod -Uri $apiUrl -Method Post -Body $body -ContentType "application/json"
} catch {
    Write-Error "Não foi possível contatar o servidor de monitoramento."
}