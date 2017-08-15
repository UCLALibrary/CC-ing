# ZBar

The zbar-ctypes package is a wrapper around the ZBar barcode scanner library. This ZBar wrapper was pulled from @brettatoms via: https://github.com/brettatoms/zbar-ctypes. However, the original code has been modified & saved to this repo, so please use the code from the CC-ing repo & not the original code from BrettAtoms.

** This package is for Python 3 only. **
To install these files, run setup.py.

### Scanning a PIL Image
```
import zbar
scanner = zbar.ImageScanner()
# disable qr code scanning (all symbol formats on by default)
scanner.set_config(zbar.SymbolType.QRCODE, zbar.Config.ENABLE, 0)
image = Image.open(path)
codes = scanner.scan_pil_image(image)
```
