# Script de démarrage PathLearn
Write-Host "🚀 Démarrage PathLearn..." -ForegroundColor Blue
docker-compose up -d
Start-Sleep -Seconds 5
docker-compose ps
Write-Host "✅ PathLearn démarré!" -ForegroundColor Green
Write-Host "📚 Course DB: localhost:5432" -ForegroundColor Yellow
Write-Host "👥 Enrollment DB: localhost:5433" -ForegroundColor Yellow