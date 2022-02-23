package config

import "github.com/spf13/viper"

type Config struct {
	MessageDataStorePath string `mapstructure: "MESSAGE_DS_PATH"` // Message Data Store Path
}

func NewConfig() Config {
	conf := viper.New()
	conf.AutomaticEnv()

	return Config{
		MessageDataStorePath: conf.GetString("MESSAGE_DS_PATH"),
	}
}
