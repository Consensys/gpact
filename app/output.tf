output "dapp_ip" {
  description = "Public IP address of dapp"
  value       = aws_instance.dapp.public_ip
}