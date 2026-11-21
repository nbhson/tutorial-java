# them PPA Adoptium
sudo apt install -y wget apt-transport-https gpg
wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | \
  sudo gpg --dearmor -o /etc/apt/trusted.gpg.d/adoptium.gpg
echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/VERSION_CODENAME/{print$2}' /etc/os-release) main" | \
  sudo tee /etc/apt/sources.list.d/adoptium.list

sudo apt update
sudo apt install temurin-25-jdk
