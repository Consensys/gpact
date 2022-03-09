output "monitor_ip" {
  description = "Public IP address of monitor"
  value       = aws_instance.relayer-monitor.public_ip
}

output "relayer_ip" {
  description = "Public IP address of relayer"
  value       = aws_instance.relayer.public_ip
}

output "msgstore_ip" {
  description = "Public IP address of msgstore"
  value       = aws_instance.msgstore.public_ip
}